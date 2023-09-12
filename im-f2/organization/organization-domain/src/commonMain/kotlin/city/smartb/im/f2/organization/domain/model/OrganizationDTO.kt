package city.smartb.im.f2.organization.domain.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.privilege.domain.role.model.Role
import city.smartb.im.f2.privilege.domain.role.model.RoleDTO
import kotlin.js.JsExport


/**
 * Representation of an organization.
 * @D2 model
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @order 10
 */
@JsExport
interface OrganizationDTO {
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId

    /**
     * Siret number of the organization.
     * @example "84488096300013"
     */
    val siret: String?

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
    val address: AddressDTO?

    /**
     * Website of the organization.
     * @example "https://smartb.city/"
     */
    val website: String?

    /**
     * Platform-specific attributes assigned to the organization.
     * @example { "otherWebsite": "https://smartb.network" }
     */
    val attributes: Map<String, String>

    /**
     * Roles of the organization.
     */
    val roles: List<RoleDTO>

    /**
     * URL pointing to the logo of the organization.
     * @example "https://d1muf25xaso8hp.cloudfront.net/https%3A%2F%2Ff9a8147b117eae8242d081b049108349.cdn.bubble.io%2Ff1673342519429x701902957290415400%2Fsmartb-logo.png?w=192&h=52&auto=compress&dpr=2&fit=max"
     */
    val logo: String?

    /**
     * Status of the organization. See [OrganizationStatus].
     * @example "VALIDATED"
     */
    val enabled: Boolean

    /**
     * Specifies if the organization is enabled or not
     * @example true
     */
    val status: String

    /**
     * Identifier of the user that disabled the organization.
     * @example null
     */
    val disabledBy: OrganizationId?

    /**
     * Creation date of the organization, as UNIX timestamp in milliseconds.
     * @example 1656938975000
     */
    val creationDate: Long

    /**
     * Disabled date of the organization, as UNIX timestamp in milliseconds.
     * @example null
     */
    val disabledDate: Long?
}

/**
 * @d2 inherit
 */
data class Organization(
    override val id: OrganizationId,
    override val siret: String?,
    override val name: String,
    override val description: String?,
    override val address: Address?,
    override val website: String?,
    override val attributes: Map<String, String>,
    override val roles: List<Role>,
    override val logo: String?,
    override val status: String,
    override val enabled: Boolean,
    override val disabledBy: OrganizationId?,
    override val creationDate: Long,
    override val disabledDate: Long?
): OrganizationDTO
