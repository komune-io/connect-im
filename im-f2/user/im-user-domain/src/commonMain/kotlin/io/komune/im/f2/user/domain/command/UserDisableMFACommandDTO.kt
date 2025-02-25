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
typealias UserDisableMFAFunction = F2Function<UserDisableMFACommand, UserDisabledMFAEvent>

/**
 * @d2 command
 * @parent [UserDisableMFAFunction]
 */
@JsExport
@JsName("UserDisableMFACommandDTO")
interface UserDisableMFACommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserDisableMFACommand(
    override val id: UserId,
): UserDisableMFACommandDTO

/**
 * @d2 event
 * @parent [UserDisableMFAFunction]
 */
@JsExport
@JsName("UserDisabledMFAEventDTO")
interface UserDisabledMFAEventDTO: Event {
    /**
     * Identifier of the updated user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserDisabledMFAEvent(
    override val id: UserId
): UserDisabledMFAEventDTO
