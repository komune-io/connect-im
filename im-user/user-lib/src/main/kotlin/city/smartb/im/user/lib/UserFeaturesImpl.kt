package city.smartb.im.user.lib

import city.smartb.im.user.domain.features.command.UserCreateFunction
import city.smartb.im.user.domain.features.command.UserDeleteFunction
import city.smartb.im.user.domain.features.command.UserDisableFunction
import city.smartb.im.user.domain.features.command.UserResetPasswordFunction
import city.smartb.im.user.domain.features.command.UserUpdateEmailFunction
import city.smartb.im.user.domain.features.command.UserUpdateFunction
import city.smartb.im.user.domain.features.command.UserUpdatePasswordFunction
import city.smartb.im.user.domain.features.query.UserExistsByEmailFunction
import city.smartb.im.user.domain.features.query.UserExistsByEmailResult
import city.smartb.im.user.domain.features.query.UserGetByEmailFunction
import city.smartb.im.user.domain.features.query.UserGetByEmailResult
import city.smartb.im.user.domain.features.query.UserGetFunction
import city.smartb.im.user.domain.features.query.UserGetResult
import city.smartb.im.user.domain.features.query.UserPageFunction
import city.smartb.im.user.lib.service.UserAggregateService
import city.smartb.im.user.lib.service.UserFinderService
import f2.dsl.fnc.f2Function
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class UserFeaturesImpl(
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService
) {
    private val logger by Logger()

    /**
     * Fetch a User by its ID.
     */
    fun userGet(): UserGetFunction = f2Function { query ->
        logger.debug("userGet: $query")
        userFinderService.userGet(query).let(::UserGetResult)
    }

    /**
     * Fetch a User by its email address.
     */
    fun userGetByEmail(): UserGetByEmailFunction = f2Function { query ->
        logger.debug("userGetByEmail: $query")
        userFinderService.userGetByEmail(query.email).let(::UserGetByEmailResult)
    }

    /**
     * Check if a User exists by its email address.
     */
    fun userExistsByEmail(): UserExistsByEmailFunction = f2Function { query ->
        logger.debug("userExistsByEmail: $query")
        UserExistsByEmailResult(
            item = userFinderService.userGetByEmail(query.email) != null
        )
    }

    /**
     * Fetch a page of users.
     */
    fun userPage(): UserPageFunction = f2Function { query ->
        logger.debug("userPage: $query")
        userFinderService.userPage(query)
    }

    /**
     * Create a User.
     */
    fun userCreate(): UserCreateFunction = f2Function { cmd ->
        logger.debug("userCreate: $cmd")
        userAggregateService.create(cmd)
    }

    /**
     * Update a User.
     */
    fun userUpdate(): UserUpdateFunction = f2Function { cmd ->
        logger.debug("userUpdate: $cmd")
        userAggregateService.update(cmd)
    }

    /**
     * Send a reset password email to a given user.
     */
    fun userResetPassword(): UserResetPasswordFunction = f2Function { cmd ->
        logger.debug("userResetPassword: $cmd")
        userAggregateService.resetPassword(cmd)
    }

    /**
     * Set the given email for a given user.
     */
    fun userUpdateEmail(): UserUpdateEmailFunction = f2Function { cmd ->
        logger.debug("userUpdateEmail: $cmd")
        userAggregateService.updateEmail(cmd)
    }

    /**
     * Set the given password for a given user ID.
     */
    fun userUpdatePassword(): UserUpdatePasswordFunction = f2Function { cmd ->
        logger.debug("userUpdatePassword: $cmd")
        userAggregateService.updatePassword(cmd)
    }

    /**
     * Upload a logo for a given user.
     */
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
    fun userDisable(): UserDisableFunction = f2Function { cmd ->
        logger.debug("userDisable: $cmd")
        userAggregateService.disable(cmd)
    }

    /**
     * Delete a user.
     */
    fun userDelete(): UserDeleteFunction = f2Function { cmd ->
        logger.debug("userDelete: $cmd")
        userAggregateService.delete(cmd)
    }
}
