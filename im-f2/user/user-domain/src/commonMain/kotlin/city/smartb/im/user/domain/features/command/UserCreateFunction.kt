package city.smartb.im.user.domain.features.command

import city.smartb.im.commons.model.Address
import city.smartb.im.core.organization.domain.model.OrganizationId
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create a new user.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 10
 */
typealias UserCreateFunction = F2Function<UserCreateCommand, UserCreatedEvent>

typealias KeycloakUserCreateCommand = i2.keycloak.f2.user.domain.features.command.UserCreateCommand
typealias KeycloakUserCreateFunction = i2.keycloak.f2.user.domain.features.command.UserCreateFunction

@JsExport
@JsName("UserCreateCommandDTO")
interface UserCreateCommandDTO: Command {
    val email: String
    val password: String?
    val givenName: String
    val familyName: String
    val address: Address?
    val phone: String?
    val roles: List<RoleIdentifier>
    val memberOf: OrganizationId?
    val attributes: Map<String, String>?
    val isEmailVerified: Boolean
    val isPasswordTemporary: Boolean
    val sendResetPassword: Boolean
    val sendVerifyEmail: Boolean
}

/**
 * @d2 command
 * @parent [UserCreateFunction]
 */
data class UserCreateCommand(
    /**
     * Email address.
     * @example [city.smartb.im.user.domain.model.User.email]
     */
    override val email: String,

    /**
     * Email address.
     * @example "zepasworde"
     */
    override val password: String? = null,

    /**
     * First name of the user.
     * @example [city.smartb.im.user.domain.model.User.givenName]
     */
    override val givenName: String,

    /**
     * Family name of the user.
     * @example [city.smartb.im.user.domain.model.User.familyName]
     */
    override val familyName: String,

    /**
     * Address of the user.
     */
    override val address: Address? = null,

    /**
     * Telephone number of the user.
     * @example [city.smartb.im.user.domain.model.User.phone]
     */
    override val phone: String? = null,

    /**
     * Roles assigned to the user.
     * @example [["admin"]]
     */
    override val roles: List<RoleIdentifier>,

    /**
     * Organization to which the user belongs.
     */
    override val memberOf: OrganizationId? = null,

    /**
     * Additional arbitrary attributes assigned to the user.
     * @example [city.smartb.im.user.domain.model.User.attributes]
     */
    override val attributes: Map<String, String>? = null,

    /**
     * False if the user has to verify their email.
     * @example true
     */
    override val isEmailVerified: Boolean = false,

    /**
     * True if the given password is temporary and has to be redefined on first login.
     * @example false
     */
    override val isPasswordTemporary: Boolean = false,

    /**
     * Send a reset_password email to the newly created user.
     */
    override val sendResetPassword: Boolean = false,

    /**
     * Send a verify_email email to the newly created user.
     */
    override val sendVerifyEmail: Boolean = true,
): UserCreateCommandDTO

@JsExport
@JsName("UserCreatedEventDTO")
interface UserCreatedEventDTO: Event {
    val id: UserId
}

/**
 * @d2 event
 * @parent [UserCreateFunction]
 */
data class UserCreatedEvent(
    /**
     * Identifier of the user.
     */
    override val id: UserId
): UserCreatedEventDTO
