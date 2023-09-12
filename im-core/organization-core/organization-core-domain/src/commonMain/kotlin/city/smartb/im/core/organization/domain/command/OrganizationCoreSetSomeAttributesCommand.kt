package city.smartb.im.core.organization.domain.command

import city.smartb.im.commons.model.OrganizationId

data class OrganizationCoreSetSomeAttributesCommand(
    val id: OrganizationId,
    val attributes: Map<String, String>
)

data class OrganizationCoreSetSomeAttributesEvent(
    val id: OrganizationId,
    val attributes: Map<String, String>
)
