package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Sets a new email for the user.
 * @d2 section
 * @parent [city.smartb.im.user.domain.D2UserCommandSection]
 */
typealias UserUpdateEmailFunction = F2Function<UserUpdateEmailCommand, UserUpdatedEmailEvent>

typealias KeycloakUserUpdateEmailCommand = i2.keycloak.f2.user.domain.features.command.UserUpdateEmailCommand
typealias KeycloakUserUpdateEmailFunction = i2.keycloak.f2.user.domain.features.command.UserUpdateEmailFunction

/**
 * @d2 command
 * @parent [UserUpdateEmailFunction]
 */
data class UserUpdateEmailCommand(
    /**
     * Identifier of the user.
     */
    val id: UserId,
    /**
     * New email of the user.
     */
    val email: String
): Command

/**
 * @d2 event
 * @parent [UserUpdateEmailFunction]
 */
data class UserUpdatedEmailEvent(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Event
