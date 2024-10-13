package io.komune.im.f2.user.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Set a new password for a user.
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 40
 */
typealias UserUpdatePasswordFunction = F2Function<UserUpdatePasswordCommand, UserUpdatedPasswordEvent>

/**
 * @d2 command
 * @parent [UserUpdatePasswordFunction]
 */
@JsExport
@JsName("UserUpdatePasswordCommandDTO")
interface UserUpdatePasswordCommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId

    /**
     * New password of the user.
     * @example "vErY_sEcUre_n3wP455W0RD"
     */
    val password: String
}

/**
 * @d2 inherit
 */
@Serializable
data class UserUpdatePasswordCommand(
    override val id: UserId,
    override val password: String
): UserUpdatePasswordCommandDTO

/**
 * @d2 event
 * @parent [UserUpdatePasswordFunction]
 */
@JsExport
@JsName("UserUpdatedPasswordEventDTO")
interface UserUpdatedPasswordEventDTO: Event {
    /**
     * Identifier of the updated user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserUpdatedPasswordEvent(
    override val id: UserId
): UserUpdatedPasswordEventDTO
