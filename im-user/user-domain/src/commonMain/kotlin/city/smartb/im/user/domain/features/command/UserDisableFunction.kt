package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Disable a user
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 70
 */
typealias UserDisableFunction = F2Function<UserDisableCommand, UserDisabledEvent>

typealias KeycloakUserDisableCommand = i2.keycloak.f2.user.domain.features.command.UserDisableCommand
typealias KeycloakUserDisableFunction = i2.keycloak.f2.user.domain.features.command.UserDisableFunction

@JsExport
@JsName("UserDisableCommandDTO")
interface UserDisableCommandDTO: Command {
    /**
     * Identifier of the user to disable.
     */
    val id: UserId

    /**
     * Identifier of the user executing the command.
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
 * @d2 command
 * @parent [UserDisableFunction]
 */
data class UserDisableCommand(
    override val id: UserId,
    override val disabledBy: UserId?,
    override val anonymize: Boolean,
    override val attributes: Map<String, String>?
): UserDisableCommandDTO

@JsExport
@JsName("UserDisabledEventDTO")
interface UserDisabledEventDTO: Event {
    /**
     * Identifier of the disabled user.
     */
    val id: UserId
}

/**
 * @d2 event
 * @parent [UserDisableFunction]
 */
data class UserDisabledEvent(
    override val id: UserId
): UserDisabledEventDTO
