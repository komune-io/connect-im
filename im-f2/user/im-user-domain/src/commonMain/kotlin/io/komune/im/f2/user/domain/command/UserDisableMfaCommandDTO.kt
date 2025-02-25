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
typealias UserDisableMfaFunction = F2Function<UserDisableMfaCommand, UserDisabledMfavent>

/**
 * @d2 command
 * @parent [UserDisableMfaFunction]
 */
@JsExport
@JsName("UserDisableMfaCommandDTO")
interface UserDisableMfaCommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserDisableMfaCommand(
    override val id: UserId,
): UserDisableMfaCommandDTO

/**
 * @d2 event
 * @parent [UserDisableMfaFunction]
 */
@JsExport
@JsName("UserDisabledMfaEventDTO")
interface UserDisabledMfaventDTO: Event {
    /**
     * Identifier of the updated user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserDisabledMfavent(
    override val id: UserId
): UserDisabledMfaventDTO
