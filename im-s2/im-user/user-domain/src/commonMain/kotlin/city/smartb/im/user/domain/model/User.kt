package city.smartb.im.user.domain.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.organization.domain.model.OrganizationRef
import city.smartb.im.organization.domain.model.OrganizationRefDTO
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import i2.keycloak.f2.role.domain.RolesCompositesModel
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Unique identifier of a user.
 * @d2 model
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 20
 * @visual json "e8322a0b-b4cf-4643-a398-c442d22504be"
 */
typealias UserId = String

@JsExport
@JsName("UserDTO")
interface UserDTO {
    val id: UserId
    val memberOf: OrganizationRefDTO?
    val email: String
    val givenName: String
    val familyName: String
    val address: AddressDTO?
    val phone: String?
    val roles: List<RoleIdentifier>
    val rolesComposites: RolesCompositesModel
    val attributes: Map<String, String>
    val enabled: Boolean
    val disabledBy: UserId?
    val creationDate: Long
    val disabledDate: Long?
}

/**
 * Representation of a user.
 * @d2 model
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 10
 */
data class User(
    /**
     * Identifier of the user.
     */
    override val id: UserId,

    /**
     * Organization Ref to which the user belongs.
     */
    override val memberOf: OrganizationRef?,

    /**
     * Email address.
     * @example "user@smartb.city"
     */
    override val email: String,

    /**
     * First name of the user.
     * @example "John"
     */
    override val givenName: String,

    /**
     * Family name of the user.
     * @example "Deuf"
     */
    override val familyName: String,

    /**
     * Address of the user.
     */
    override val address: Address?,

    /**
     * Telephone number of the user.
     * @example "06 12 34 56 78"
     */
    override val phone: String?,

    /**
     * Roles of the user.
     */
    override val roles: List<RoleIdentifier>,

    override val rolesComposites: RolesCompositesModel,
    /**
     * Platform-specific attributes assigned to the user
     * @example { "age": "42" }
     */
    override val attributes: Map<String, String>,

    /**
     * Specifies if the user is enabled or not.
     * @example true
     */
    override val enabled: Boolean,

    /**
     * Identifier of the user that disabled the user.
     * @example null
     */
    override val disabledBy: UserId?,

    /**
     * Creation date of the user, as UNIX timestamp in milliseconds.
     * @example 1656938975000
     */
    override val creationDate: Long,

    /**
     * Disabled date of the user, as UNIX timestamp in milliseconds.
     * @example null
     */
    override val disabledDate: Long?
): UserDTO
