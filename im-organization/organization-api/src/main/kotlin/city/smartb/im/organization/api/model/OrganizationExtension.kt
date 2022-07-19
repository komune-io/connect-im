package city.smartb.im.organization.api.model

import city.smartb.im.commons.model.AddressBase
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationRef
import i2.keycloak.f2.group.domain.model.GroupModel

private val imGroupAttributes = listOf(
    Organization::address.name,
    Organization::creationDate.name,
    Organization::description.name,
    Organization::siret.name,
    Organization::website.name
)

fun GroupModel.toOrganization() = Organization(
    id = id,
    name = name,
    siret = attributes[Organization::siret.name].orEmpty(),
    address = attributes[Organization::address.name]?.parseJsonTo(AddressBase::class.java).orEmpty(),
    description = attributes[Organization::description.name],
    website = attributes[Organization::website.name],
    attributes = attributes.filterKeys { key -> key !in imGroupAttributes },
    roles = roles,
    creationDate = attributes[Organization::creationDate.name]?.toLong() ?: 0
)

fun GroupModel.toOrganizationRef() = OrganizationRef(
    id = id,
    name = name,
    roles = roles
)
