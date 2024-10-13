package io.komune.im.f2.organization.domain.model

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.AddressDTO
import io.komune.im.commons.model.OrganizationId
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.privilege.domain.role.model.RoleDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable


/**
 * Representation of an organization.
 * @D2 model
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
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
     * @example "Komune"
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
     * @example "https://komune.io/"
     */
    val website: String?

    /**
     * Platform-specific attributes assigned to the organization.
     * @example { "otherWebsite": "https://komune.io" }
     */
    val attributes: Map<String, String>

    /**
     * Roles of the organization.
     */
    val roles: List<RoleDTO>

    /**
     * URL pointing to the logo of the organization.
     * @example "https://komune.io/logo.png"
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
@Serializable
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
