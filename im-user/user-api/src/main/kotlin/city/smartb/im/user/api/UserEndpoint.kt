package city.smartb.im.user.api

import city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE
import city.smartb.im.commons.auth.policies.enforce
import city.smartb.im.commons.auth.policies.verify
import city.smartb.im.commons.auth.policies.verifyAfter
import city.smartb.im.user.api.policies.UserPoliciesEnforcer
import city.smartb.im.user.domain.features.command.UserCreateFunction
import city.smartb.im.user.domain.features.command.UserDeleteFunction
import city.smartb.im.user.domain.features.command.UserDisableFunction
import city.smartb.im.user.domain.features.command.UserResetPasswordFunction
import city.smartb.im.user.domain.features.command.UserUpdateEmailFunction
import city.smartb.im.user.domain.features.command.UserUpdateFunction
import city.smartb.im.user.domain.features.command.UserUpdatePasswordFunction
import city.smartb.im.user.domain.features.query.UserExistsByEmailFunction
import city.smartb.im.user.domain.features.query.UserGetByEmailFunction
import city.smartb.im.user.domain.features.query.UserGetFunction
import city.smartb.im.user.domain.features.query.UserPageFunction
import city.smartb.im.user.lib.UserFeaturesImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import s2.spring.utils.logger.Logger
import javax.annotation.security.RolesAllowed

/**
 * @d2 service
 * @parent [city.smartb.im.user.domain.D2UserPage]
 */
@RestController
@RequestMapping
@Configuration
class UserEndpoint(
    private val userFeaturesImpl: UserFeaturesImpl,
    private val policiesEnforcer: UserPoliciesEnforcer,
) {
    private val logger by Logger()

    /**
     * Fetch a User by its ID.
     */
    @Bean
    fun userGet(): UserGetFunction = verifyAfter(userFeaturesImpl.userGet()) { result ->
        policiesEnforcer.checkGet(result.item)
    }

    /**
     * Fetch a User by its email address.
     */
    @Bean
    fun userGetByEmail(): UserGetByEmailFunction = verify(userFeaturesImpl.userGetByEmail()) { query ->
        policiesEnforcer.checkGet()
    }

    /**
     * Check if a User exists by its email address.
     */
    @Bean
    fun userExistsByEmail(): UserExistsByEmailFunction = verify(userFeaturesImpl.userExistsByEmail()) { query ->
        policiesEnforcer.checkGet()
    }


    /**
     * Fetch a page of users.
     */

    /**
     * Fetch a page of users.
     */
    @Bean
    fun userPage(): UserPageFunction = enforce(userFeaturesImpl.userPage()) { query ->
        policiesEnforcer.enforcePage(query)
    }


    /**
     * Create a User.
     */

    /**
     * Create a User.
     */
    @Bean
    fun userCreate(): UserCreateFunction = verify(userFeaturesImpl.userCreate()) { command ->
        policiesEnforcer.checkCreate()
    }


    /**
     * Update a User.
     */

    /**
     * Update a User.
     */
    @Bean
    fun userUpdate(): UserUpdateFunction = verify(userFeaturesImpl.userUpdate()) { command ->
        policiesEnforcer.checkUpdate(command.id)
    }


    /**
     * Send a reset password email to a given user.
     */

    /**
     * Send a reset password email to a given user.
     */
    @Bean
    fun userResetPassword(): UserResetPasswordFunction = verify(userFeaturesImpl.userResetPassword()) { command ->
        policiesEnforcer.checkUpdate(command.id)
    }


    /**
     * Set the given email for a given user.
     */

    /**
     * Set the given email for a given user.
     */
    @Bean
    fun userUpdateEmail(): UserUpdateEmailFunction = verify(userFeaturesImpl.userUpdateEmail()) { command ->
        policiesEnforcer.checkUpdate(command.id)
    }


    /**
     * Set the given password for a given user ID.
     */

    /**
     * Set the given password for a given user ID.
     */
    @Bean
    fun userUpdatePassword(): UserUpdatePasswordFunction = verify(userFeaturesImpl.userUpdatePassword()) { command ->
        policiesEnforcer.checkUpdate(command.id)
    }


    /**
     * Upload a logo for a given user.
     */

    /**
     * Upload a logo for a given user.
     */
//    @RolesAllowed(Roles.WRITE_USER)
//    @PostMapping("/userUploadLogo")
//    suspend fun userUploadLogo(
//        @RequestPart("command") cmd: UserUploadLogoCommand,
//        @RequestPart("file") file: FilePart
//    ): UserUploadedLogoEvent {
//        logger.debug("userUploadLogo: $cmd")
//        return userAggregateService.uploadLogo(cmd, file.contentByteArray())
//    }

    /**
     * Disable a user.
     */
    @Bean
    fun userDisable(): UserDisableFunction = verify(userFeaturesImpl.userDisable()) { command ->
        policiesEnforcer.checkDisable(command.id)
    }

    /**
     * Delete a user.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun userDelete(): UserDeleteFunction = verify(userFeaturesImpl.userDelete()) { command ->
        policiesEnforcer.checkDelete(command.id)
    }

}
