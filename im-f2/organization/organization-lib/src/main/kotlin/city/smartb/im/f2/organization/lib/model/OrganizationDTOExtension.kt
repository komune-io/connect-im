package city.smartb.im.f2.organization.lib.model

import city.smartb.im.core.organization.domain.model.Organization
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import city.smartb.im.f2.organization.domain.model.OrganizationRef
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

val imOrganizationAttributes = listOf(
    Organization::displayName.name,
    OrganizationDTOBase::address.name,
    OrganizationDTOBase::creationDate.name,
    OrganizationDTOBase::description.name,
    OrganizationDTOBase::disabledBy.name,
    OrganizationDTOBase::disabledDate.name,
    OrganizationDTOBase::enabled.name,
    OrganizationDTOBase::logo.name,
    OrganizationDTOBase::siret.name,
    OrganizationDTOBase::website.name,
)

suspend fun Organization.toDTO(
    getRole: suspend (RoleIdentifier) -> RoleDTOBase
) = coroutineScope {
    val roles = roles.map { async { getRole(it) } }

    OrganizationDTOBase(
        id = id,
        name = identifier,
        siret = attributes[OrganizationDTOBase::siret.name].orEmpty(),
        address = address,
        description = description,
        website = attributes[OrganizationDTOBase::website.name],
        attributes = attributes.filterKeys { key -> key !in imOrganizationAttributes },
        roles = roles.awaitAll(),
        logo = attributes[OrganizationDTOBase::logo.name],
        enabled = enabled,
        disabledBy = attributes[OrganizationDTOBase::disabledBy.name],
        creationDate = attributes[OrganizationDTOBase::creationDate.name]?.toLong() ?: 0,
        disabledDate = attributes[OrganizationDTOBase::disabledDate.name]?.toLong()
    )
}

fun Organization.toRef() = OrganizationRef(
    id = id,
    name = identifier,
    roles = roles
)
