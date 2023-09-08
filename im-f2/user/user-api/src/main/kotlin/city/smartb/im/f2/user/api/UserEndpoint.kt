package city.smartb.im.f2.user.api

import city.smartb.im.commons.auth.policies.f2Function
import city.smartb.im.f2.user.domain.UserApi
import city.smartb.im.f2.user.domain.command.UserCreateFunction
import city.smartb.im.f2.user.domain.command.UserDeleteFunction
import city.smartb.im.f2.user.domain.command.UserDisableFunction
import city.smartb.im.f2.user.domain.command.UserResetPasswordFunction
import city.smartb.im.f2.user.domain.command.UserUpdateEmailFunction
import city.smartb.im.f2.user.domain.command.UserUpdateFunction
import city.smartb.im.f2.user.domain.command.UserUpdatePasswordFunction
import city.smartb.im.f2.user.domain.query.UserExistsByEmailFunction
import city.smartb.im.f2.user.domain.query.UserExistsByEmailResult
import city.smartb.im.f2.user.domain.query.UserGetByEmailFunction
import city.smartb.im.f2.user.domain.query.UserGetByEmailResult
import city.smartb.im.f2.user.domain.query.UserGetFunction
import city.smartb.im.f2.user.domain.query.UserGetResult
import city.smartb.im.f2.user.domain.query.UserPageFunction
import city.smartb.im.f2.user.domain.query.UserPageResult
import city.smartb.im.f2.user.lib.UserAggregateService
import city.smartb.im.f2.user.lib.UserFinderService
import f2.dsl.cqrs.page.OffsetPagination
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import s2.spring.utils.logger.Logger

@RestController
@RequestMapping
@Configuration
class UserEndpoint(
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
    private val policiesEnforcer: UserPoliciesEnforcer,
): UserApi {
    private val logger by Logger()

    @Bean
    override fun userGet(): UserGetFunction = f2Function { query ->
        logger.info("userGet: $query")
        val user = userFinderService.getOrNull(query.id)
        policiesEnforcer.checkGet(user)
        UserGetResult(user)
    }

    @Bean
    override fun userGetByEmail(): UserGetByEmailFunction = f2Function { query ->
        logger.info("userGetByEmail: $query")
        val user = userFinderService.getByEmailOrNull(query.email)
        policiesEnforcer.checkGet(user)
        UserGetByEmailResult(user)
    }

    @Bean
    override fun userExistsByEmail(): UserExistsByEmailFunction = f2Function { query ->
        logger.info("userExistsByEmail: $query")
        policiesEnforcer.checkGet()
        UserExistsByEmailResult(userFinderService.getByEmailOrNull(query.email) != null)
    }

    @Bean
    override fun userPage(): UserPageFunction = f2Function { query ->
        logger.info("userPage: $query")

        policiesEnforcer.checkPage()
        val enforcedQuery = policiesEnforcer.enforcePageQuery(query)

        val roles = buildSet {
            enforcedQuery.roles?.let(::addAll)
            enforcedQuery.role?.let(::add)
        }.ifEmpty { null }

        userFinderService.page(
            organizationIds = enforcedQuery.organizationId?.let(::listOf),
            roles = roles,
            search = enforcedQuery.search,
            attributes = enforcedQuery.attributes,
            withDisabled = enforcedQuery.withDisabled,
            offset = OffsetPagination(
                offset = enforcedQuery.offset ?: 0,
                limit = enforcedQuery.limit ?: Int.MAX_VALUE
            ),
        ).let { UserPageResult(it.items, it.total) }
    }

    @Bean
    override fun userCreate(): UserCreateFunction = f2Function { command ->
        logger.info("userCreate: $command")
        policiesEnforcer.checkCreate(command.memberOf)
        userAggregateService.create(command)
    }

    @Bean
    override fun userUpdate(): UserUpdateFunction = f2Function { command ->
        logger.info("userUpdate: $command")
        policiesEnforcer.checkUpdate(command.id)
        userAggregateService.update(command)
    }

    @Bean
    override fun userResetPassword(): UserResetPasswordFunction = f2Function { command ->
        logger.info("userResetPassword: $command")
        policiesEnforcer.checkUpdate(command.id)
        userAggregateService.resetPassword(command)
    }

    @Bean
    override fun userUpdateEmail(): UserUpdateEmailFunction = f2Function { command ->
        logger.info("userUpdateEmail: $command")
        policiesEnforcer.checkUpdate(command.id)
        userAggregateService.updateEmail(command)
    }

    @Bean
    override fun userUpdatePassword(): UserUpdatePasswordFunction = f2Function { command ->
        logger.info("userUpdatePassword: $command")
        policiesEnforcer.checkUpdate(command.id)
        userAggregateService.updatePassword(command)
    }

    @Bean
    override fun userDisable(): UserDisableFunction = f2Function { command ->
        logger.info("userDisable: $command")
        policiesEnforcer.checkDisable(command.id)
        userAggregateService.disable(command)
    }

    @Bean
    override fun userDelete(): UserDeleteFunction = f2Function { command ->
        logger.info("userDelete: $command")
        policiesEnforcer.checkDelete(command.id)
        userAggregateService.delete(command)
    }
}
