package city.smartb.im.user.domain.features.command

import city.smartb.im.commons.model.AddressBase
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Creates a new user.
 * @d2 section
 * @parent [city.smartb.im.user.domain.D2UserCommandSection]
 */
typealias UserCreateFunction = F2Function<UserCreateCommand, UserCreatedEvent>

typealias KeycloakUserCreateCommand = i2.keycloak.f2.user.domain.features.command.UserCreateCommand
typealias KeycloakUserCreateFunction = i2.keycloak.f2.user.domain.features.command.UserCreateFunction

/**
 * @d2 command
 * @parent [UserCreateFunction]
 */
data class UserCreateCommand(
    /**
     * Email address.
     * @example [city.smartb.im.user.domain.model.User.email]
     */
    val email: String,

    /**
     * First name of the user.
     * @example [city.smartb.im.user.domain.model.User.givenName]
     */
    val givenName: String,

    /**
     * Family name of the user.
     * @example [city.smartb.im.user.domain.model.User.familyName]
     */
    val familyName: String,

    /**
     * Address of the user.
     */
    val address: AddressBase?,

    /**
     * Telephone number of the user.
     * @example [city.smartb.im.user.domain.model.User.phone]
     */
    val phone: String?,

    /**
     * Roles assigned to the user.
     * @example [["admin"]]
     */
    val roles: List<String>,

    /**
     * Send a validation email to the user.
     * @example [city.smartb.im.user.domain.model.User.sendEmailLink]
     */
    val sendEmailLink: Boolean,

    /**
     * Organization to which the user belongs.
     */
    val memberOf: OrganizationId?,
): Command

/**
 * @d2 event
 * @parent [UserCreateFunction]
 */
data class UserCreatedEvent(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Event
