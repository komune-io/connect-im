package city.smartb.im.core.organization.domain.command

import city.smartb.im.commons.model.OrganizationId

data class OrganizationSetSomeAttributesCommand(
    val id: OrganizationId,
    val attributes: Map<String, String>
)

data class OrganizationSetSomeAttributesEvent(
    val id: OrganizationId,
    val attributes: Map<String, String>
)
