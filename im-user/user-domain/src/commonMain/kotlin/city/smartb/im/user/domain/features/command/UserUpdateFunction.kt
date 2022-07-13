package city.smartb.im.user.domain.features.command

import city.smartb.im.commons.model.AddressBase
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Updates the user.
 * @d2 section
 * @parent [city.smartb.im.user.domain.D2UserCommandSection]
 */
typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdatedEvent>

typealias KeycloakUserUpdateCommand = i2.keycloak.f2.user.domain.features.command.UserUpdateCommand
typealias KeycloakUserUpdateFunction = i2.keycloak.f2.user.domain.features.command.UserUpdateFunction

/**
 * @d2 command
 * @parent [UserUpdateFunction]
 */
data class UserUpdateCommand(
    /**
     * Identifier of the user.
     */
    val id: UserId,

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
     * Send a validation email to the user.
     * @example [city.smartb.im.user.domain.model.User.sendEmailLink]
     */
    val sendEmailLink: Boolean,

    /**
     * Organization to which the user belongs.
     */
    val memberOf: OrganizationId?,

    /**
     * Add roles to the user.
     * @example [["admin"]]
     */
    val roles: List<String>,

    /**
     * Additional arbitrary attributes assigned to the user.
     * @example [city.smartb.im.user.domain.model.User.attributes]
     */
    val attributes: Map<String, String>?
): Command

/**
 * @d2 event
 * @parent [UserUpdateFunction]
 */
data class UserUpdatedEvent(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Event
