package city.smartb.im.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import city.smartb.im.user.domain.model.UserId

/**
 * Sets a new password for the user.
 * @d2 section
 * @parent [city.smartb.im.user.domain.D2UserCommandSection]
 */
typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserResetPasswordEvent>

typealias KeycloakUserResetPasswordCommand = i2.keycloak.f2.user.domain.features.command.UserResetPasswordCommand
typealias KeycloakUserResetPasswordFunction = i2.keycloak.f2.user.domain.features.command.UserResetPasswordFunction

/**
 * @d2 command
 * @parent [UserResetPasswordFunction]
 */
data class UserResetPasswordCommand(
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
 * @parent [UserResetPasswordFunction]
 */
data class UserResetPasswordEvent(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Event
