package city.smartb.im.organization.api

import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.service.OrganizationMapper

class OrganizationMapperImpl : OrganizationMapper<Organization, Organization> {

    override fun from(organization: Organization): Organization {
        return organization
    }

    override fun to(model: Organization): Organization {
        return model
    }
}
