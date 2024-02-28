package io.komune.im.core.organization.domain.model

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.UserId

typealias OrganizationIdentifier = String

data class OrganizationModel(
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
