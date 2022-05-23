package city.smartb.im.organization.api.model

import city.smartb.im.commons.model.AddressBase
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationBase
import city.smartb.im.organization.domain.model.OrganizationRef
import i2.keycloak.f2.group.domain.model.GroupModel

fun GroupModel.toOrganization() = OrganizationBase(
    id = id,
    name = name,
    siret = attributes[Organization::siret.name]?.first() ?: "",
    address = attributes[Organization::address.name]?.first()?.parseJsonTo(AddressBase::class.java).orEmpty(),
    description = attributes[Organization::description.name]?.firstOrNull(),
    website = attributes[Organization::website.name]?.firstOrNull(),
    roles = roles.toTypedArray()
)

fun GroupModel.toOrganizationRef() = OrganizationRef(
    id = id,
    name = name,
    roles = roles
)
