package city.smartb.im.f2.organization.domain.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.core.organization.domain.model.OrganizationId
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName

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
    val logo: String?
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
data class OrganizationDTOBase(
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
     * URL pointing to the logo of the organization.
     * @example "https://d1muf25xaso8hp.cloudfront.net/https%3A%2F%2Ff9a8147b117eae8242d081b049108349.cdn.bubble.io%2Ff1673342519429x701902957290415400%2Fsmartb-logo.png?w=192&h=52&auto=compress&dpr=2&fit=max"
     */
    override val logo: String?,

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
