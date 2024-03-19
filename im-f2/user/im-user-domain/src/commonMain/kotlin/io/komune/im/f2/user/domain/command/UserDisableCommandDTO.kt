package io.komune.im.f2.user.domain.command

import io.komune.im.commons.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Disable a user
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 70
 */
typealias UserDisableFunction = F2Function<UserDisableCommand, UserDisabledEvent>

/**
 * @d2 command
 * @parent [UserDisableFunction]
 */
@JsExport
@JsName("UserDisableCommandDTO")
interface UserDisableCommandDTO: Command {
    /**
     * Identifier of the user to disable.
     */
    val id: UserId

    /**
     * Identifier of the user executing the command. If null, will use the authenticated user.
     * @example "d61e6ab6-12d2-40ec-ba15-534aa7302a2b"
     */
    val disabledBy: UserId?

    /**
     * Whether to anonymize the personal data of the user or not.
     * This will remove or blank all sensible fields except custom attributes. To anonymize custom attributes, see `attributes` field.
     * @example true
     */
    val anonymize: Boolean

    /**
     * Custom attributes to update during anonymization.
     * Use this field only if `anonymize` is set to true.
     * @example { "age": null }
     */
    val attributes: Map<String, String>?
}

/**
 * @d2 inherit
 */
data class UserDisableCommand(
    override val id: UserId,
    override val disabledBy: UserId?,
    override val anonymize: Boolean,
    override val attributes: Map<String, String>?
): UserDisableCommandDTO

/**
 * @d2 command
 * @parent [UserDisableFunction]
 */
@JsExport
interface UserDisabledEventDTO: Event {
    /**
     * Id of the disabled user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
data class UserDisabledEvent(
    override val id: UserId
): UserDisabledEventDTO
