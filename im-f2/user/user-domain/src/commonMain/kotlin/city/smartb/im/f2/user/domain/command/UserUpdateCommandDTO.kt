package city.smartb.im.f2.user.domain.command

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Update a user.
 * @d2 function
 * @parent [city.smartb.im.f2.user.domain.D2UserPage]
 * @order 20
 */
typealias UserUpdateFunction = F2Function<UserUpdateCommandDTOBase, UserUpdatedEventDTOBase>

/**
 * @d2 command
 * @parent [UserUpdateFunction]
 */
@JsExport
@JsName("UserUpdateCommandDTO")
interface UserUpdateCommandDTO: Command {
    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.id]
     */
    val id: UserId

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
     */
    val address: AddressDTO?

    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.phone]
     */
    val phone: String?

    /**
     * Roles assigned to the user.
     * @example [["tr_orchestrator_admin"]]
     */
    val roles: List<RoleIdentifier>

    /**
     * @ref [city.smartb.im.f2.user.domain.model.UserDTO.attributes]
     */
    val attributes: Map<String, String>?
}

/**
 * @d2 inherit
 */
data class UserUpdateCommandDTOBase(
    override val id: UserId,
    override val givenName: String,
    override val familyName: String,
    override val address: Address?,
    override val phone: String?,
    override val roles: List<RoleIdentifier>,
    override val attributes: Map<String, String>?
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
data class UserUpdatedEventDTOBase(
    override val id: UserId
): UserUpdatedEventDTO
