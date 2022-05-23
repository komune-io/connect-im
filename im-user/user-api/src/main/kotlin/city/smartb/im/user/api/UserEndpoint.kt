package city.smartb.im.user.api

import city.smartb.i2.spring.boot.auth.PermissionEvaluator
import city.smartb.im.api.config.Roles
import city.smartb.im.user.api.service.UserAggregateService
import city.smartb.im.user.api.service.UserFinderService
import city.smartb.im.user.domain.features.command.UserCreateFunction
import city.smartb.im.user.domain.features.command.UserResetPasswordFunction
import city.smartb.im.user.domain.features.command.UserUpdateFunction
import city.smartb.im.user.domain.features.query.UserGetFunction
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.features.query.UserPageFunction
import f2.dsl.fnc.f2Function
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 service
 * @title User/Entrypoints
 */
@Configuration
class UserEndpoint(
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
    private val permissionEvaluator: PermissionEvaluator
) {
    /**
     * Fetches a User by its ID.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userGet(): UserGetFunction = f2Function { query ->
        userFinderService.userGet(query)
    }

    /**
     * Fetches a page of users.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userPage(): UserPageFunction = f2Function { query ->
        userFinderService.userPage(query)
    }

    /**
     * Creates a User.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userCreate(): UserCreateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userAggregateService.userCreate(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Updates a User.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userUpdate(): UserUpdateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userAggregateService.userUpdate(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Sets the given password for the given user ID.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userResetPassword(): UserResetPasswordFunction = f2Function { cmd ->
        val user = userFinderService.userGet(UserGetQuery(cmd.id)).item
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(user?.memberOf?.id)) {
            userAggregateService.userResetPassword(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }
}
