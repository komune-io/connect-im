package city.smartb.im.organization.api

import city.smartb.im.commons.model.AddressBase
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.model.imGroupAttributes
import city.smartb.im.organization.lib.model.orEmpty
import city.smartb.im.organization.lib.service.OrganizationMapper
import i2.keycloak.f2.group.domain.model.GroupModel

class OrganizationMapperImpl: OrganizationMapper<Organization> {

    override fun toOrganization(group: GroupModel): Organization {
        return Organization(
            id = group.id,
            name = group.name,
            siret = group.attributes[Organization::siret.name],
            address = group.attributes[Organization::address.name]?.parseJsonTo(AddressBase::class.java).orEmpty(),
            description = group.attributes[Organization::description.name],
            website = group.attributes[Organization::website.name],
            attributes = group.attributes.filterKeys { key -> key !in imGroupAttributes },
            roles = group.roles,
            enabled = group.attributes[Organization::enabled.name]?.toBoolean() ?: true,
            creationDate = group.attributes[Organization::creationDate.name]?.toLong() ?: 0
        )
    }
}
