package city.smartb.im.f2.organization.lib.model

import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.utils.mapAsyncDeferred
import city.smartb.im.core.organization.domain.model.OrganizationModel
import city.smartb.im.f2.organization.domain.model.Organization
import city.smartb.im.f2.organization.domain.model.OrganizationRef
import city.smartb.im.f2.organization.domain.model.OrganizationStatus
import city.smartb.im.f2.privilege.domain.role.model.Role
import kotlinx.coroutines.awaitAll

val imOrganizationAttributes = listOf(
    OrganizationModel::displayName.name,
    Organization::address.name,
    Organization::creationDate.name,
    Organization::description.name,
    Organization::disabledBy.name,
    Organization::disabledDate.name,
    Organization::enabled.name,
    Organization::logo.name,
    Organization::siret.name,
    Organization::website.name,
)

suspend fun OrganizationModel.toDTO(
    getRole: suspend (RoleIdentifier) -> Role
): Organization {
    val roles = roles.mapAsyncDeferred(getRole)

    return Organization(
        id = id,
        name = identifier,
        siret = attributes[Organization::siret.name].orEmpty(),
        address = address,
        description = description,
        website = attributes[Organization::website.name],
        attributes = attributes.filterKeys { key -> key !in imOrganizationAttributes },
        roles = roles.awaitAll(),
        logo = attributes[Organization::logo.name],
        status = attributes[Organization::status.name] ?: OrganizationStatus.PENDING.name,
        enabled = enabled,
        disabledBy = attributes[Organization::disabledBy.name],
        creationDate = attributes[Organization::creationDate.name]?.toLong() ?: 0,
        disabledDate = attributes[Organization::disabledDate.name]?.toLong(),
    )
}

fun OrganizationModel.toRef() = OrganizationRef(
    id = id,
    name = identifier,
    roles = roles
)
