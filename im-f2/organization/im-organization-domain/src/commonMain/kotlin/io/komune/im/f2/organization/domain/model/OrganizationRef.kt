package io.komune.im.f2.organization.domain.model

import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Short representation of an organization.
 * @D2 model
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
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

@Serializable
data class OrganizationRef(
    override val id: OrganizationId,
    override val name: String,
    override val roles: List<RoleIdentifier>
): OrganizationRefDTO
