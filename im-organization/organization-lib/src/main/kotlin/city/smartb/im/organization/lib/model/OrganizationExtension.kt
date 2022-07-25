package city.smartb.im.organization.lib.model

import city.smartb.im.organization.domain.model.OrganizationRef
import i2.keycloak.f2.group.domain.model.GroupModel

fun GroupModel.toOrganizationRef() = OrganizationRef(
    id = id,
    name = name,
    roles = roles
)
