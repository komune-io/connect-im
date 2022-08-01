package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Set a new password for a user.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 40
 */
typealias UserUpdatePasswordFunction = F2Function<UserUpdatePasswordCommand, UserUpdatedPasswordEvent>

typealias KeycloakUserUpdatePasswordCommand = i2.keycloak.f2.user.domain.features.command.UserUpdatePasswordCommand
typealias KeycloakUserUpdatePasswordFunction = i2.keycloak.f2.user.domain.features.command.UserUpdatePasswordFunction

@JsExport
@JsName("UserUpdatePasswordCommandDTO")
interface UserUpdatePasswordCommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId

    /**
     * New password of the user.
     */
    val password: String
}

/**
 * @d2 command
 * @parent [UserUpdatePasswordFunction]
 */
data class UserUpdatePasswordCommand(
    override val id: UserId,
    override val password: String
): UserUpdatePasswordCommandDTO

@JsExport
@JsName("UserUpdatedPasswordEventDTO")
interface UserUpdatedPasswordEventDTO: Event {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 event
 * @parent [UserUpdatePasswordFunction]
 */
data class UserUpdatedPasswordEvent(
    override val id: UserId
): UserUpdatedPasswordEventDTO
