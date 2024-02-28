package io.komune.im.core.organization.domain.command

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId

data class OrganizationCoreDefineCommand(
    val id: OrganizationId?,
    val identifier: String,
    val displayName: String = identifier,
    val description: String?,
    val address: Address?,
    val roles: List<String>?,
    val parentOrganizationId: OrganizationId? = null,
    val attributes: Map<String, String>?,
)

data class OrganizationCoreDefinedEvent(
    val id: OrganizationId
)
