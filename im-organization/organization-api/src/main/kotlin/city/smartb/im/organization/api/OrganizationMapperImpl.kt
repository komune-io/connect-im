package city.smartb.im.organization.api

import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.service.OrganizationMapper

class OrganizationMapperImpl : OrganizationMapper<Organization, Organization> {

    override fun mapModel(organization: Organization): Organization {
        return organization
    }

    override fun mapOrganization(model: Organization): Organization {
        return model
    }
}
