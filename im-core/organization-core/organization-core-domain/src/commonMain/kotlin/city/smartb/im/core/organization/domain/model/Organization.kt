package city.smartb.im.core.organization.domain.model

import city.smartb.im.commons.auth.UserId
import city.smartb.im.commons.model.Address
import city.smartb.im.core.privilege.domain.model.RoleIdentifier

/**
 * @d2 hidden
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias OrganizationId = String
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
