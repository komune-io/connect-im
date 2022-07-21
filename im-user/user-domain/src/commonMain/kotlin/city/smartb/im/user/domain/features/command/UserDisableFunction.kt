package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Disable a user
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 70
 */
typealias UserDisableFunction = F2Function<UserDisableCommand, UserDisabledEvent>

typealias KeycloakUserDisableCommand = i2.keycloak.f2.user.domain.features.command.UserDisableCommand
typealias KeycloakUserDisableFunction = i2.keycloak.f2.user.domain.features.command.UserDisableFunction

/**
 * @d2 command
 * @parent [UserDisableFunction]
 */
data class UserDisableCommand(
    /**
     * Identifier of the user to disable.
     */
    val id: UserId
): Command

/**
 * @d2 event
 * @parent [UserDisableFunction]
 */
data class UserDisabledEvent(
    /**
     * Identifier of the disabled user.
     */
    val id: UserId
): Event
