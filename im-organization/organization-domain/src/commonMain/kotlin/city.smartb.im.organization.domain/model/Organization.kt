package city.smartb.im.organization.domain.model

import i2.keycloak.f2.group.domain.model.GroupId
import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressBase

/**
 * Unique identifier of the organization.
 * @d2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationModelSection]
 * @order 20
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias OrganizationId = GroupId

/**
 * Representation of the organization.
 * @D2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationModelSection]
 */
interface Organization {
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId

    /**
     * Siret number of the organization.
     * @example "84488096300013"
     */
    val siret: String

    /**
     * Official name of the organization.
     * @example "SmartB"
     */
    val name: String

    /**
     * Description of the organization.
     * @example "We use technology, design and systems thinking to tackle global sustainability & financing challenges."
     */
    val description: String?

    /**
     * Address of the organization.
     */
    val address: Address?

    /**
     * Website of the organization.
     * @example "https://smartb.city/"
     */
    val website: String?

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [["admin", "write_user", "read_user", "write_organization", "read_organization"]]
     */
    val roles: Array<String>?
}

data class OrganizationBase(
    override val id: OrganizationId,
    override val siret: String,
    override val name: String,
    override val description: String?,
    override val address: AddressBase?,
    override val website: String?,
    override val roles: Array<String>?
): Organization
