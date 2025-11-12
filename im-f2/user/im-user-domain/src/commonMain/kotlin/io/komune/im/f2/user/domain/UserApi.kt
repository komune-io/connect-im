package io.komune.im.f2.user.domain

import io.komune.im.f2.user.domain.command.UserCreateFunction
import io.komune.im.f2.user.domain.command.UserDeleteFunction
import io.komune.im.f2.user.domain.command.UserDisableFunction
import io.komune.im.f2.user.domain.command.UserDisableMfaFunction
import io.komune.im.f2.user.domain.command.UserEnableFunction
import io.komune.im.f2.user.domain.command.UserResetPasswordFunction
import io.komune.im.f2.user.domain.command.UserUpdateEmailFunction
import io.komune.im.f2.user.domain.command.UserUpdateFunction
import io.komune.im.f2.user.domain.command.UserUpdatePasswordFunction
import io.komune.im.f2.user.domain.query.UserExistsByEmailFunction
import io.komune.im.f2.user.domain.query.UserGetByEmailFunction
import io.komune.im.f2.user.domain.query.UserGetFunction
import io.komune.im.f2.user.domain.query.UserPageFunction

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
    /** Disable multifactor authentication **/
    fun userDisableMfa(): UserDisableMfaFunction
    /** Disable a user */
    fun userDisable(): UserDisableFunction
    /** Enable a user */
    fun userEnable(): UserEnableFunction
    /** Delete a user */
    fun userDelete(): UserDeleteFunction
}
