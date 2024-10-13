package io.komune.im.f2.user.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Send an email to a user for them to reset their password.
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 50
 */
typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserResetPasswordEvent>

/**
 * @d2 command
 * @parent [UserResetPasswordFunction]
 */
@JsExport
@JsName("UserResetPasswordCommandDTO")
interface UserResetPasswordCommandDTO: Command {
    /**
     * Id of the user to reset the password for.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserResetPasswordCommand(
    override val id: UserId
): UserResetPasswordCommandDTO

/**
 * @d2 event
 * @parent [UserResetPasswordFunction]
 */
@JsExport
@JsName("UserResetPasswordEventDTO")
interface UserResetPasswordEventDTO: Event {
    /**
     * Id of the user that received the email.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserResetPasswordEvent(
    override val id: UserId
): UserResetPasswordEventDTO
