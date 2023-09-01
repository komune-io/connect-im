package city.smartb.im.organization.lib.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.orEmpty
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationRef
import i2.keycloak.f2.group.domain.model.GroupModel

val imGroupAttributes = listOf(
    Organization::address.name,
    Organization::creationDate.name,
    Organization::description.name,
    Organization::disabledBy.name,
    Organization::disabledDate.name,
    Organization::enabled.name,
    Organization::siret.name,
    Organization::website.name
)

fun GroupModel.toOrganizationRef() = OrganizationRef(
    id = id,
    name = name,
    roles = roles.assignedRoles
)

fun GroupModel.toOrganization() = Organization(
    id = id,
    name = name,
    siret = attributes[Organization::siret.name].orEmpty(),
    address = attributes[Organization::address.name]?.parseJsonTo(Address::class.java).orEmpty(),
    description = attributes[Organization::description.name],
    website = attributes[Organization::website.name],
    attributes = attributes.filterKeys { key -> key !in imGroupAttributes },
    roles = roles.assignedRoles,
    rolesComposites = roles,
    enabled = enabled,
    disabledBy = attributes[Organization::disabledBy.name],
    creationDate = attributes[Organization::creationDate.name]?.toLong() ?: 0,
    disabledDate = attributes[Organization::disabledDate.name]?.toLong()
)
