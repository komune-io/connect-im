package city.smartb.im.f2.user.domain.command

import city.smartb.im.commons.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport

/**
 * Set a new email for a user.
 * @d2 section
 * @parent [city.smartb.im.f2.user.domain.D2UserPage]
 * @order 30
 */
typealias UserUpdateEmailFunction = F2Function<UserUpdateEmailCommandDTOBase, UserUpdatedEmailEventDTOBase>

/**
 * @d2 command
 * @parent [UserUpdateEmailFunction]
 */
@JsExport
interface UserUpdateEmailCommandDTO: Command {
    /**
     * Id of the user.
     */
    val id: UserId

    /**
     * New email of the user.
     */
    val email: String

    /**
     * Whether to send a verification email after a successful update.
     * @example true
     */
    val sendVerificationEmail: Boolean
}

/**
 * @d2 inherit
 */
data class UserUpdateEmailCommandDTOBase(
    override val id: UserId,
    override val email: String,
    override val sendVerificationEmail: Boolean = true
): UserUpdateEmailCommandDTO

/**
 * @d2 event
 * @parent [UserUpdateEmailFunction]
 */
@JsExport
interface UserUpdatedEmailEventDTO: Event {
    /**
     * Id of the user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
data class UserUpdatedEmailEventDTOBase(
    override val id: UserId
): UserUpdatedEmailEventDTO
