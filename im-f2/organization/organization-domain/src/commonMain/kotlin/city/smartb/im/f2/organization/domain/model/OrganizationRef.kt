package city.smartb.im.f2.organization.domain.model

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("OrganizationRefDTO")
interface OrganizationRefDTO {
    val id: OrganizationId
    val name: String
    val roles: List<String>
}

/**
 * Short representation of an organization.
 * @D2 model
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @order 30
 */
data class OrganizationRef(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * Name of the organization.
     * @example [OrganizationDTOBase.name]
     */
    override val name: String,

    /**
     * Assigned and effective roles of the organization.
     * @example [OrganizationDTOBase.roles]
     */
    override val roles: List<RoleIdentifier>
): OrganizationRefDTO
