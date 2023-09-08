package city.smartb.im.core.user.domain.model

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId

data class User(
    val id: UserId,
    val memberOf: OrganizationId?,
    val email: String,
    val givenName: String,
    val familyName: String,
    val roles: List<RoleIdentifier>,
    val attributes: Map<String, String>,
    val enabled: Boolean,
    val disabledBy: UserId?,
    val creationDate: Long,
    val disabledDate: Long?
)
