package city.smartb.im.f2.organization.domain.model

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import kotlin.js.JsExport

/**
 * Short representation of an organization.
 * @D2 model
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @order 30
 */
@JsExport
interface OrganizationRefDTO {
    /**
     * @ref [OrganizationDTO.id]
     */
    val id: OrganizationId

    /**
     * @ref [OrganizationDTO.name]
     */
    val name: String

    /**
     * @ref [OrganizationDTO.roles]
     */
    val roles: List<String>
}

data class OrganizationRef(
    override val id: OrganizationId,
    override val name: String,
    override val roles: List<RoleIdentifier>
): OrganizationRefDTO
