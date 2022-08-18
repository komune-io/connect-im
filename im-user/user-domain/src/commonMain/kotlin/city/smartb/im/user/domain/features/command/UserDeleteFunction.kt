package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Delete a user
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 80
 */
typealias UserDeleteFunction = F2Function<UserDeleteCommand, UserDeletedEvent>

typealias KeycloakUserDeleteCommand = i2.keycloak.f2.user.domain.features.command.UserDeleteCommand
typealias KeycloakUserDeleteFunction = i2.keycloak.f2.user.domain.features.command.UserDeleteFunction

@JsExport
@JsName("UserDeleteCommandDTO")
interface UserDeleteCommandDTO: Command {
    /**
     * Identifier of the user to delete.
     */
    val id: UserId
}

/**
 * @d2 command
 * @parent [UserDeleteFunction]
 */
data class UserDeleteCommand(
    override val id: UserId
): UserDeleteCommandDTO

@JsExport
@JsName("UserDeletedEventDTO")
interface UserDeletedEventDTO: Event {
    /**
     * Identifier of the deleted user.
     */
    val id: UserId
}

/**
 * @d2 event
 * @parent [UserDeleteFunction]
 */
data class UserDeletedEvent(
    override val id: UserId
): UserDeletedEventDTO
