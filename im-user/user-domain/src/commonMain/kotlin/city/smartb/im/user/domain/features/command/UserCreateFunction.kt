package city.smartb.im.user.domain.features.command

import city.smartb.im.commons.model.Address
import city.smartb.im.organization.domain.model.OrganizationId
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
    val roles: List<String>
    val sendEmailLink: Boolean
    val memberOf: OrganizationId?
    val attributes: Map<String, String>?
    val isEmailVerified: Boolean
    val isPasswordTemporary: Boolean
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
    override val password: String?,

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
    override val address: Address?,

    /**
     * Telephone number of the user.
     * @example [city.smartb.im.user.domain.model.User.phone]
     */
    override val phone: String?,

    /**
     * Roles assigned to the user.
     * @example [["admin"]]
     */
    override val roles: List<String>,

    /**
     * Send a validation email to the user.
     * @example [city.smartb.im.user.domain.model.User.sendEmailLink]
     */
    override val sendEmailLink: Boolean,

    /**
     * Organization to which the user belongs.
     */
    override val memberOf: OrganizationId?,

    /**
     * Additional arbitrary attributes assigned to the user.
     * @example [city.smartb.im.user.domain.model.User.attributes]
     */
    override val attributes: Map<String, String>?,

    /**
     * False if the user has to verify their email.
     * @example true
     */
    override val isEmailVerified: Boolean,

    /**
     * True if the given password is temporary and has to be redefined on first login.
     * @example false
     */
    override val isPasswordTemporary: Boolean = false
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
