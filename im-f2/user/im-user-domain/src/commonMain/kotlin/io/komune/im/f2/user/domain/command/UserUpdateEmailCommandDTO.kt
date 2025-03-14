package io.komune.im.f2.user.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.UserId
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Set a new email for a user.
 * @d2 section
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 30
 */
typealias UserUpdateEmailFunction = F2Function<UserUpdateEmailCommand, UserUpdatedEmailEvent>

/**
 * @d2 command
 * @parent [UserUpdateEmailFunction]
 */
@JsExport
interface UserUpdateEmailCommandDTO: Command {
    /**
     * Id of the user.
     */
    val id: UserId

    /**
     * New email of the user.
     */
    val email: String

    /**
     * Whether to send a verification email after a successful update.
     * @example true
     */
    val sendVerificationEmail: Boolean
}

/**
 * @d2 inherit
 */
@Serializable
data class UserUpdateEmailCommand(
    override val id: UserId,
    override val email: String,
    override val sendVerificationEmail: Boolean = true
): UserUpdateEmailCommandDTO

/**
 * @d2 event
 * @parent [UserUpdateEmailFunction]
 */
@JsExport
interface UserUpdatedEmailEventDTO: Event {
    /**
     * Id of the user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserUpdatedEmailEvent(
    override val id: UserId
): UserUpdatedEmailEventDTO
