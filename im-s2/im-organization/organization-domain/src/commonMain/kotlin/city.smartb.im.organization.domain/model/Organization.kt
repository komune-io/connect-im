package city.smartb.im.organization.domain.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.role.domain.RolesCompositesModel
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Unique identifier of an organization.
 * @d2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 20
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias OrganizationId = GroupId

@JsExport
@JsName("OrganizationDTO")
interface OrganizationDTO {
    val id: OrganizationId
    val siret: String?
    val name: String
    val description: String?
    val address: AddressDTO?
    val website: String?
    val attributes: Map<String, String>
    val roles: List<RoleIdentifier>
    val rolesComposites: RolesCompositesModel
    val enabled: Boolean
    val disabledBy: OrganizationId?
    val creationDate: Long
    val disabledDate: Long?
}

/**
 * Representation of an organization.
 * @D2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 10
 */
data class Organization(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * Siret number of the organization.
     * @example "84488096300013"
     */
    override val siret: String?,

    /**
     * Official name of the organization.
     * @example "SmartB"
     */
    override val name: String,

    /**
     * Description of the organization.
     * @example "We use technology, design and systems thinking to tackle global sustainability & financing challenges."
     */
    override val description: String?,

    /**
     * Address of the organization.
     */
    override val address: Address?,

    /**
     * Website of the organization.
     * @example "https://smartb.city/"
     */
    override val website: String?,

    /**
     * Platform-specific attributes assigned to the organization.
     * @example { "otherWebsite": "https://smartb.network" }
     */
    override val attributes: Map<String, String>,

    /**
     * Roles of the organization.
     */
    override val roles: List<RoleIdentifier>,

    /**
     * All composite roles of the organization.
     */
    override val rolesComposites: RolesCompositesModel,

    /**
     * Specifies if the organization is enabled or not
     * @example true
     */
    override val enabled: Boolean,

    /**
     * Identifier of the user that disabled the organization.
     * @example null
     */
    override val disabledBy: OrganizationId?,

    /**
     * Creation date of the organization, as UNIX timestamp in milliseconds.
     * @example 1656938975000
     */
    override val creationDate: Long,

    /**
     * Disabled date of the organization, as UNIX timestamp in milliseconds.
     * @example null
     */
    override val disabledDate: Long?
): OrganizationDTO
