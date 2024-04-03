package io.komune.im.f2.organization.lib.model

import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.utils.mapAsyncDeferred
import io.komune.im.core.organization.domain.model.OrganizationModel
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.privilege.domain.role.model.Role
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
