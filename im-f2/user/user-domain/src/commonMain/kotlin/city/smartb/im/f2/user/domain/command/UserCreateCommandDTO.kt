package city.smartb.im.f2.user.domain.command

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create a new user.
 * @d2 function
 * @parent [city.smartb.im.f2.user.domain.D2UserPage]
 * @order 10
 */
typealias UserCreateFunction = F2Function<UserCreateCommandDTOBase, UserCreatedEventDTOBase>

/**
 * @d2 command
 * @parent [UserCreateFunction]
 */
@JsExport
@JsName("UserCreateCommandDTO")
interface UserCreateCommandDTO: Command {
    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.email]
     */
    val email: String

    /**
     * Password of the user.
     * @example "zepasworde"
     * @default null
     */
    val password: String?

    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.givenName]
     */
    val givenName: String

    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.familyName]
     */
    val familyName: String

    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.address]
     * @default null
     */
    val address: Address?

    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.phone]
     * @default null
     */
    val phone: String?

    /**
     * Identifier of the [roles][city.smartb.im.f2.privilege.domain.role.model.RoleDTO] to assign to the user.
     * @example [["tr_orchestrator_admin"]]
     */
    val roles: List<RoleIdentifier>

    /**
     * Id of the [organization][city.smartb.im.f2.organization.domain.model.OrganizationDTO] to which the user belongs.
     */
    val memberOf: OrganizationId?

    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.attributes]
     * @default null
     */
    val attributes: Map<String, String>?

    /**
     * False if the user has to verify their email.
     * @example true
     * @default false
     */
    val isEmailVerified: Boolean

    /**
     * True if the given password is temporary and has to be redefined on first login.
     * @example true
     * @default false
     */
    val isPasswordTemporary: Boolean

    /**
     * Send a reset_password email to the newly created user.
     * @example true
     * @default false
     */
    val sendResetPassword: Boolean

    /**
     * Send a verify_email email to the newly created user.
     * @example true
     * @default true
     */
    val sendVerifyEmail: Boolean
}

/**
 * @d2 inherit
 */
data class UserCreateCommandDTOBase(
    override val email: String,
    override val password: String? = null,
    override val givenName: String,
    override val familyName: String,
    override val address: Address? = null,
    override val phone: String? = null,
    override val roles: List<RoleIdentifier>,
    override val memberOf: OrganizationId? = null,
    override val attributes: Map<String, String>? = null,
    override val isEmailVerified: Boolean = false,
    override val isPasswordTemporary: Boolean = false,
    override val sendResetPassword: Boolean = false,
    override val sendVerifyEmail: Boolean = true
): UserCreateCommandDTO

/**
 * @d2 event
 * @parent [UserCreateFunction]
 */
@JsExport
interface UserCreatedEventDTO: Event {
    /**
     * Id of the created user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
data class UserCreatedEventDTOBase(
    override val id: UserId
): UserCreatedEventDTO
