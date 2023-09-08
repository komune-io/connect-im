package city.smartb.im.f2.user.domain.command

import city.smartb.im.commons.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Send an email to a user for them to reset their password.
 * @d2 function
 * @parent [city.smartb.im.f2.user.domain.D2UserPage]
 * @order 50
 */
typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommandDTOBase, UserResetPasswordEventDTOBase>

/**
 * @d2 command
 * @parent [UserResetPasswordFunction]
 */
@JsExport
@JsName("UserResetPasswordCommandDTO")
interface UserResetPasswordCommandDTO: Command {
    /**
     * Id of the user to reset the password for.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
data class UserResetPasswordCommandDTOBase(
    override val id: UserId
): UserResetPasswordCommandDTO

/**
 * @d2 event
 * @parent [UserResetPasswordFunction]
 */
@JsExport
@JsName("UserResetPasswordEventDTO")
interface UserResetPasswordEventDTO: Event {
    /**
     * Id of the user that received the email.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
data class UserResetPasswordEventDTOBase(
    override val id: UserId
): UserResetPasswordEventDTO
