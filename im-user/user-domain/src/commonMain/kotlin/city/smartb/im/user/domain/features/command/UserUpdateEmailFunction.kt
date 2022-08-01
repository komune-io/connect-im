package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Set a new email for a user.
 * @d2 section
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 30
 */
typealias UserUpdateEmailFunction = F2Function<UserUpdateEmailCommand, UserUpdatedEmailEvent>

typealias KeycloakUserUpdateEmailCommand = i2.keycloak.f2.user.domain.features.command.UserUpdateEmailCommand
typealias KeycloakUserUpdateEmailFunction = i2.keycloak.f2.user.domain.features.command.UserUpdateEmailFunction

@JsExport
@JsName("UserUpdateEmailCommandDTO")
interface UserUpdateEmailCommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId

    /**
     * New email of the user.
     */
    val email: String
}

/**
 * @d2 command
 * @parent [UserUpdateEmailFunction]
 */
data class UserUpdateEmailCommand(
    override val id: UserId,
    override val email: String
): UserUpdateEmailCommandDTO

@JsExport
@JsName("UserUpdatedEmailEventDTO")
interface UserUpdatedEmailEventDTO: Event {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 event
 * @parent [UserUpdateEmailFunction]
 */
data class UserUpdatedEmailEvent(
    override val id: UserId
): UserUpdatedEmailEventDTO
