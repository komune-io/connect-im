package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Send an email to a user for them to reset their password.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 50
 */
typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserResetPasswordEvent>

/**
 * @d2 command
 * @parent [UserResetPasswordFunction]
 */
data class UserResetPasswordCommand(
    /**
     * Identifier of the user.
     */
    val id: UserId
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
