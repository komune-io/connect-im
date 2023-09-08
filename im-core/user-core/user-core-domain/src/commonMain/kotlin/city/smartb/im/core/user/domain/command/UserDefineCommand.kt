package city.smartb.im.core.user.domain.command

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId

data class UserDefineCommand(
    val id: UserId?,
    val email: String? = null,
    val password: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val roles: List<RoleIdentifier>? = null,
    val memberOf: OrganizationId? = null,
    val attributes: Map<String, String>? = null,
    val isEmailVerified: Boolean? = null,
    val isPasswordTemporary: Boolean = false
)

data class UserDefinedEvent(
    val id: UserId
)
