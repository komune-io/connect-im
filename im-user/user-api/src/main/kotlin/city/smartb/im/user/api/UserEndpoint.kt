package city.smartb.im.user.api

import city.smartb.i2.spring.boot.auth.PermissionEvaluator
import city.smartb.im.api.config.Roles
import city.smartb.im.commons.utils.contentByteArray
import city.smartb.im.user.api.service.UserAggregateService
import city.smartb.im.user.api.service.UserFinderService
import city.smartb.im.user.domain.features.command.UserCreateFunction
import city.smartb.im.user.domain.features.command.UserResetPasswordFunction
import city.smartb.im.user.domain.features.command.UserUpdateEmailFunction
import city.smartb.im.user.domain.features.command.UserUpdateFunction
import city.smartb.im.user.domain.features.command.UserUpdatePasswordFunction
import city.smartb.im.user.domain.features.command.UserUploadLogoCommand
import city.smartb.im.user.domain.features.command.UserUploadedLogoEvent
import city.smartb.im.user.domain.features.query.UserGetFunction
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.features.query.UserPageFunction
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import s2.spring.utils.logger.Logger
import javax.annotation.security.RolesAllowed

/**
 * @d2 service
 * @title User/Entrypoints
 */
@RestController
@RequestMapping
@Configuration
class UserEndpoint(
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
    private val permissionEvaluator: PermissionEvaluator
) {
    private val logger by Logger()

    /**
     * Fetches a User by its ID.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userGet(): UserGetFunction = f2Function { query ->
        logger.info("userGet: $query")
        userFinderService.userGet(query)
    }

    /**
     * Fetches a page of users.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userPage(): UserPageFunction = f2Function { query ->
        logger.info("userPage: $query")
        userFinderService.userPage(query)
    }

    /**
     * Creates a User.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userCreate(): UserCreateFunction = f2Function { cmd ->
        logger.info("userCreate: $cmd")
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userAggregateService.create(cmd)
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
        logger.info("userUpdate: $cmd")
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userAggregateService.update(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Send a reset password email to the given user.
     */
    @Bean
    fun userResetPassword(): UserResetPasswordFunction = f2Function { cmd ->
        logger.info("userResetPassword: $cmd")
        userAggregateService.resetPassword(cmd)
    }

    /**
     * Sets the given email for the given user.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userUpdateEmail(): UserUpdateEmailFunction = f2Function { cmd ->
        logger.info("userUpdateEmail: $cmd")
        val user = userFinderService.userGet(UserGetQuery(cmd.id)).item
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(user?.memberOf?.id)) {
            userAggregateService.updateEmail(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Sets the given password for the given user ID.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userUpdatePassword(): UserUpdatePasswordFunction = f2Function { cmd ->
        logger.info("userUpdatePassword: $cmd")
        val user = userFinderService.userGet(UserGetQuery(cmd.id)).item
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(user?.memberOf?.id)) {
            userAggregateService.updatePassword(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Upload a logo for a given user
     */
    @RolesAllowed(Roles.WRITE_USER)
    @PostMapping("/userUploadLogo")
    suspend fun userUploadLogo(
        @RequestPart("command") cmd: UserUploadLogoCommand,
        @RequestPart("file") file: FilePart
    ): UserUploadedLogoEvent {
        logger.info("userUploadLogo: $cmd")
        return userAggregateService.uploadLogo(cmd, file.contentByteArray())
    }
}
