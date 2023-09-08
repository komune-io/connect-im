package city.smartb.im.core.organization.domain.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId

typealias OrganizationIdentifier = String

data class Organization(
    val id: OrganizationId,
    val identifier: OrganizationIdentifier,
    val displayName: String,
    val description: String?,
    val address: Address?,
    val attributes: Map<String, String>,
    val roles: List<RoleIdentifier>,
    val enabled: Boolean,
    val disabledBy: UserId?,
    val creationDate: Long,
    val disabledDate: Long?
)
