package city.smartb.im.organization.domain.model

import city.smartb.im.commons.model.Address
import i2.keycloak.f2.group.domain.model.GroupId

/**
 * Unique identifier of an organization.
 * @d2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 20
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias OrganizationId = GroupId

interface OrganizationDTO {
    val id: OrganizationId
    val siret: String?
    val name: String
    val description: String?
    val address: Address?
    val website: String?
    val attributes: Map<String, String>
    val roles: List<String>?
    val enabled: Boolean
    val creationDate: Long
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
     * Platform-specific attributes assigned to the organization
     * @example { "otherWebsite": "https://smartb.network" }
     */
    override val attributes: Map<String, String>,

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [["admin", "write_user", "read_user", "write_organization", "read_organization"]]
     */
    override val roles: List<String>?,

    /**
     * Specifies if the organization is enabled or not
     * @example true
     */
    override val enabled: Boolean,

    /**
     * Creation date of the organization, as UNIX timestamp in milliseconds.
     * @example 1656938975000
     */
    override val creationDate: Long
): OrganizationDTO
