package io.komune.im.core.user.domain.model

import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.UserId

data class UserModel(
    val id: UserId,
    val memberOf: OrganizationId?,
    val email: String,
    val givenName: String,
    val familyName: String,
    val roles: List<RoleIdentifier>,
    val mfa: List<String>,
    val attributes: Map<String, String>,
    val enabled: Boolean,
    val disabledBy: UserId?,
    val creationDate: Long,
    val disabledDate: Long?,
    val isApiKey: Boolean
)
