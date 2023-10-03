package city.smartb.im.f2.user.domain

import city.smartb.im.f2.user.domain.command.UserCreateFunction
import city.smartb.im.f2.user.domain.command.UserDeleteFunction
import city.smartb.im.f2.user.domain.command.UserDisableFunction
import city.smartb.im.f2.user.domain.command.UserResetPasswordFunction
import city.smartb.im.f2.user.domain.command.UserUpdateEmailFunction
import city.smartb.im.f2.user.domain.command.UserUpdateFunction
import city.smartb.im.f2.user.domain.command.UserUpdatePasswordFunction
import city.smartb.im.f2.user.domain.query.UserExistsByEmailFunction
import city.smartb.im.f2.user.domain.query.UserGetByEmailFunction
import city.smartb.im.f2.user.domain.query.UserGetFunction
import city.smartb.im.f2.user.domain.query.UserPageFunction

/**
 * @d2 api
 * @parent [D2UserPage]
 */
interface UserApi: UserQueryApi, UserCommandApi

interface UserQueryApi {
    /** Fetch a User by its ID */
    fun userGet(): UserGetFunction
    /** Fetch a User by its email address */
    fun userGetByEmail(): UserGetByEmailFunction
    /** Check if a User exists by its email address */
    fun userExistsByEmail(): UserExistsByEmailFunction
    /** Fetch a page of users */
    fun userPage(): UserPageFunction

}

interface UserCommandApi {
    /** Create a User */
    fun userCreate(): UserCreateFunction
    /** Update a User */
    fun userUpdate(): UserUpdateFunction
    /** Send a reset password email to a given user */
    fun userResetPassword(): UserResetPasswordFunction
    /** Set the given email for a given user */
    fun userUpdateEmail(): UserUpdateEmailFunction
    /** Set the given password for a given user ID */
    fun userUpdatePassword(): UserUpdatePasswordFunction
    /** Disable a user */
    fun userDisable(): UserDisableFunction
    /** Delete a user */
    fun userDelete(): UserDeleteFunction
}
