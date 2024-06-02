package io.komune.im.f2.user.domain.command

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.AddressDTO
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.OrganizationId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Update a user.
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 20
 */
typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdatedEvent>

/**
 * @d2 command
 * @parent [UserUpdateFunction]
 */
@JsExport
@JsName("UserUpdateCommandDTO")
interface UserUpdateCommandDTO: Command {
    /**
     * @ref [io.komune.im.f2.user.domain.model.UserDTO.id]
     */
    val id: UserId

    /**
     * @ref [io.komune.im.f2.user.domain.model.UserDTO.givenName]
     */
    val givenName: String

    /**
     * @ref [io.komune.im.f2.user.domain.model.UserDTO.familyName]
     */
    val familyName: String

    /**
     * @ref [io.komune.im.f2.user.domain.model.UserDTO.address]
     */
    val address: AddressDTO?

    /**
     * @ref [io.komune.im.f2.user.domain.model.UserDTO.phone]
     */
    val phone: String?

    /**
     * Roles assigned to the user.
     * @example [["tr_orchestrator_admin"]]
     */
    val roles: List<RoleIdentifier>

    /**
     * Id of the [organization][io.komune.im.f2.organization.domain.model.OrganizationDTO] to which the user belongs.
     */
    val memberOf: OrganizationId?

    /**
     * @ref [io.komune.im.f2.user.domain.model.UserDTO.attributes]
     */
    val attributes: Map<String, String>?
}

/**
 * @d2 inherit
 */
@Serializable
data class UserUpdateCommand(
    override val id: UserId,
    override val givenName: String,
    override val familyName: String,
    override val address: Address?,
    override val phone: String?,
    override val roles: List<RoleIdentifier>,
    override val memberOf: OrganizationId?,
    override val attributes: Map<String, String>?,
): UserUpdateCommandDTO

/**
 * @d2 event
 * @parent [UserUpdateFunction]
 */
@JsExport
interface UserUpdatedEventDTO: Event {
    /**
     * Id of the updated user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserUpdatedEvent(
    override val id: UserId
): UserUpdatedEventDTO
