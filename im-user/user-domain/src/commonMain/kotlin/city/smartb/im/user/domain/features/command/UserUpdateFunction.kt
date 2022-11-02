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
 * Update a user.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 20
 */
typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdatedEvent>

typealias KeycloakUserUpdateCommand = i2.keycloak.f2.user.domain.features.command.UserUpdateCommand
typealias KeycloakUserUpdateFunction = i2.keycloak.f2.user.domain.features.command.UserUpdateFunction

@JsExport
@JsName("UserUpdateCommandDTO")
interface UserUpdateCommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId

    /**
     * First name of the user.
     * @example [city.smartb.im.user.domain.model.User.givenName]
     */
    val givenName: String

    /**
     * Family name of the user.
     * @example [city.smartb.im.user.domain.model.User.familyName]
     */
    val familyName: String

    /**
     * Address of the user.
     */
    val address: Address?

    /**
     * Telephone number of the user.
     * @example [city.smartb.im.user.domain.model.User.phone]
     */
    val phone: String?

    /**
     * Organization to which the user belongs.
     */
    val memberOf: OrganizationId?

    /**
     * Add roles to the user.
     * @example [["admin"]]
     */
    val roles: List<String>

    /**
     * Additional arbitrary attributes assigned to the user.
     * @example [city.smartb.im.user.domain.model.User.attributes]
     */
    val attributes: Map<String, String>?
}

/**
 * @d2 command
 * @parent [UserUpdateFunction]
 */
data class UserUpdateCommand(
    override val id: UserId,
    override val givenName: String,
    override val familyName: String,
    override val address: Address?,
    override val phone: String?,
    override val memberOf: OrganizationId?,
    override val roles: List<String>,
    override val attributes: Map<String, String>?
): UserUpdateCommandDTO

@JsExport
@JsName("UserUpdatedEventDTO")
interface UserUpdatedEventDTO: Event {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 event
 * @parent [UserUpdateFunction]
 */
data class UserUpdatedEvent(
    override val id: UserId
): UserUpdatedEventDTO
