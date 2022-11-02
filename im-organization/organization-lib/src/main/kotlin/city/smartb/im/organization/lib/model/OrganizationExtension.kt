package city.smartb.im.organization.lib.model

import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationRef
import i2.keycloak.f2.group.domain.model.GroupModel

val imGroupAttributes = listOf(
    Organization::address.name,
    Organization::creationDate.name,
    Organization::description.name,
    Organization::enabled.name,
    Organization::siret.name,
    Organization::website.name
)

fun GroupModel.toOrganizationRef() = OrganizationRef(
    id = id,
    name = name,
    roles = roles.assignedRoles
)
