package city.smartb.im.user.api

import city.smartb.i2.spring.boot.auth.PermissionEvaluator
import city.smartb.im.api.config.Roles
import city.smartb.im.user.domain.features.command.UserCreateFunction
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
    private val permissionEvaluator: PermissionEvaluator
) {
    private val logger by Logger()

    /**
     * Fetch a User by its ID.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userGet(): UserGetFunction = userFeaturesImpl.userGet()

    /**
     * Fetch a User by its email address.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userGetByEmail(): UserGetByEmailFunction = userFeaturesImpl.userGetByEmail()

    /**
     * Check if a User exists by its email address.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userExistsByEmail(): UserExistsByEmailFunction = userFeaturesImpl.userExistsByEmail()

    /**
     * Fetch a page of users.
     */
    @Bean
    @RolesAllowed(Roles.READ_USER)
    fun userPage(): UserPageFunction = userFeaturesImpl.userPage()

    /**
     * Create a User.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userCreate(): UserCreateFunction = userFeaturesImpl.userCreate()

    /**
     * Update a User.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userUpdate(): UserUpdateFunction = userFeaturesImpl.userUpdate()

    /**
     * Send a reset password email to a given user.
     */
    @Bean
    fun userResetPassword(): UserResetPasswordFunction = userFeaturesImpl.userResetPassword()

    /**
     * Set the given email for a given user.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userUpdateEmail(): UserUpdateEmailFunction = userFeaturesImpl.userUpdateEmail()

    /**
     * Set the given password for a given user ID.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userUpdatePassword(): UserUpdatePasswordFunction = userFeaturesImpl.userUpdatePassword()

    /**
     * Upload a logo for a given user.
     */
//    @RolesAllowed(Roles.WRITE_USER)
//    @PostMapping("/userUploadLogo")
//    suspend fun userUploadLogo(
//        @RequestPart("command") cmd: UserUploadLogoCommand,
//        @RequestPart("file") file: FilePart
//    ): UserUploadedLogoEvent {
//        logger.info("userUploadLogo: $cmd")
//        return userAggregateService.uploadLogo(cmd, file.contentByteArray())
//    }

    /**
     * Disable a user.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_USER)
    fun userDisable(): UserDisableFunction = userFeaturesImpl.userDisable()
}
