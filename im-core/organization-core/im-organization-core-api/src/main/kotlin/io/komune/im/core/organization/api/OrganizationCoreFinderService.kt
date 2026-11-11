package io.komune.im.core.organization.api

import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import f2.spring.exception.NotFoundException
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.utils.matches
import io.komune.im.commons.utils.page
import io.komune.im.core.commons.CoreService
import io.komune.im.core.organization.api.model.toOrganization
import io.komune.im.core.organization.domain.model.OrganizationModel
import io.komune.im.infra.redis.CacheName
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.stereotype.Service

@Service
class OrganizationCoreFinderService : CoreService(CacheName.Organization) {

    suspend fun getOrNull(
        id: OrganizationId
    ): OrganizationModel? = query(id, "Error while fetching organization [$id]") {
        val client = keycloakClientProvider.getClient()
        @Suppress("SwallowedException")
        try {
            client.group(id).toRepresentation().toOrganization()
        } catch (e: JakartaNotFoundException) {
            null
        }
    }

    suspend fun get(id: OrganizationId): OrganizationModel =
        getOrNull(id) ?: throw NotFoundException(name = "Organization", id = id)

    suspend fun page(
        filter: OrganizationCoreFilter = OrganizationCoreFilter(),
        offset: OffsetPagination? = null,
    ): PageDTO<OrganizationModel> = with(filter) {
        val client = keycloakClientProvider.getClient()

        val compositeRoles = client.roles().list().associate { role ->
            val composites = client.role(role.name).realmRoleComposites.mapNotNull { it.name }
            role.name to composites.toList()
        }

        val groups = client.groups()
            .groups("", 0, Int.MAX_VALUE, false)
            .asSequence()
            .map(GroupRepresentation::toOrganization)
            .filter { organization ->
                organization.id.matches(ids)
                    && (isDisabledIncluded || organization.enabled)
                    && (attributes == null || attributes.all { (key, match) -> match(organization.attributes[key]) })
                    && (identifier == null || organization.identifier.contains(identifier, true))
                    && (name == null || organization.displayName.contains(name, true))
                    && organization.roles.flatMap { compositeRoles[it].orEmpty() + it }.toSet().matches(roles)
            }
            .sortedByDescending(OrganizationModel::creationDate)
            .toList()

        groups.page(offset)
    }
}

/** What [OrganizationCoreFinderService.page] keeps; a null criterion keeps everything. */
data class OrganizationCoreFilter(
    val ids: Collection<OrganizationId>? = null,
    val identifier: String? = null,
    val name: String? = null,
    val roles: Collection<RoleIdentifier>? = null,
    val attributes: Map<String, (String?) -> Boolean>? = null,
    val isDisabledIncluded: Boolean = false,
)
