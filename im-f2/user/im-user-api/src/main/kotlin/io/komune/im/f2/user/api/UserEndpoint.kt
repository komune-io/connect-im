package io.komune.im.f2.user.api

import f2.dsl.cqrs.page.OffsetPagination
import io.komune.im.commons.auth.policies.f2Function
import io.komune.im.f2.user.domain.UserApi
import io.komune.im.f2.user.domain.command.UserCreateFunction
import io.komune.im.f2.user.domain.command.UserDeleteFunction
import io.komune.im.f2.user.domain.command.UserDisableFunction
import io.komune.im.f2.user.domain.command.UserDisableMfaFunction
import io.komune.im.f2.user.domain.command.UserResetPasswordFunction
import io.komune.im.f2.user.domain.command.UserUpdateEmailFunction
import io.komune.im.f2.user.domain.command.UserUpdateFunction
import io.komune.im.f2.user.domain.command.UserUpdatePasswordFunction
import io.komune.im.f2.user.domain.query.UserExistsByEmailFunction
import io.komune.im.f2.user.domain.query.UserExistsByEmailResult
import io.komune.im.f2.user.domain.query.UserGetByEmailFunction
import io.komune.im.f2.user.domain.query.UserGetByEmailResult
import io.komune.im.f2.user.domain.query.UserGetFunction
import io.komune.im.f2.user.domain.query.UserGetResult
import io.komune.im.f2.user.domain.query.UserPageFunction
import io.komune.im.f2.user.domain.query.UserPageResult
import io.komune.im.f2.user.lib.UserAggregateService
import io.komune.im.f2.user.lib.UserFinderService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
@Configuration
class UserEndpoint(
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
    private val policiesEnforcer: UserPoliciesEnforcer,
): UserApi {
    private val logger = LoggerFactory.getLogger(UserEndpoint::class.java)

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
            organizationName = enforcedQuery.organizationName,
            roles = roles,
            name = enforcedQuery.name,
            email = enforcedQuery.email,
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
        val cmd = policiesEnforcer.enforceUpdate(command)
        userAggregateService.update(cmd)
    }

    @Bean
    override fun userResetPassword(): UserResetPasswordFunction = f2Function { command ->
        logger.info("userResetPassword: $command")
        policiesEnforcer.checkUpdate(command.id)
        userAggregateService.resetPassword(command)
    }

    @Bean
    override fun userDisableMfa(): UserDisableMfaFunction = f2Function { command ->
        logger.info("userDisableMfa: $command")
        policiesEnforcer.checkDisableMfa(command.id)
        userAggregateService.disableMultiFactorAuthentication(command)
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
