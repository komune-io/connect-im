package io.komune.im.core.organization.domain.command

import io.komune.im.commons.model.OrganizationId

data class OrganizationCoreSetSomeAttributesCommand(
    val id: OrganizationId,
    val attributes: Map<String, String>
)

data class OrganizationCoreSetSomeAttributesEvent(
    val id: OrganizationId,
    val attributes: Map<String, String>
)
