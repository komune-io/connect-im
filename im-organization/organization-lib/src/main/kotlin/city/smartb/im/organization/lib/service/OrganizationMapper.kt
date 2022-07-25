package city.smartb.im.organization.lib.service

import i2.keycloak.f2.group.domain.model.GroupModel

interface OrganizationMapper<MODEL> {

    fun toOrganization(group: GroupModel): MODEL
}
