package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Sets a new password for the user.
 * @d2 section
 * @parent [city.smartb.im.user.domain.D2UserCommandSection]
 */
typealias UserUpdatePasswordFunction = F2Function<UserUpdatePasswordCommand, UserUpdatedPasswordEvent>

typealias KeycloakUserUpdatePasswordCommand = i2.keycloak.f2.user.domain.features.command.UserUpdatePasswordCommand
typealias KeycloakUserUpdatePasswordFunction = i2.keycloak.f2.user.domain.features.command.UserUpdatePasswordFunction

/**
 * @d2 command
 * @parent [UserUpdatePasswordFunction]
 */
data class UserUpdatePasswordCommand(
    /**
     * Identifier of the user.
     */
    val id: UserId,
    /**
     * New password of the user.
     */
    val password: String
): Command

/**
 * @d2 event
 * @parent [UserUpdatePasswordFunction]
 */
data class UserUpdatedPasswordEvent(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Event