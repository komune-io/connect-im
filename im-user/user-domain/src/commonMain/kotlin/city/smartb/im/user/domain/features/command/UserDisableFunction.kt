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
}

/**
 * @d2 command
 * @parent [UserDisableFunction]
 */
data class UserDisableCommand(
    override val id: UserId
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
