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
typealias UserConfigureMFAFunction = F2Function<UserConfigureMFACommand, UserConfiguredMFAEvent>

/**
 * @d2 command
 * @parent [UserConfigureMFAFunction]
 */
@JsExport
@JsName("UserConfigureMFACommandDTO")
interface UserConfigureMFACommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserConfigureMFACommand(
    override val id: UserId,
): UserConfigureMFACommandDTO

/**
 * @d2 event
 * @parent [UserConfigureMFAFunction]
 */
@JsExport
@JsName("UserConfiguredMFAEventDTO")
interface UserConfiguredMFAEventDTO: Event {
    /**
     * Identifier of the updated user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserConfiguredMFAEvent(
    override val id: UserId
): UserConfiguredMFAEventDTO
