package city.smartb.im.core.organization.api

import city.smartb.im.commons.utils.matches
import city.smartb.im.commons.utils.page
import city.smartb.im.core.commons.CoreService
import city.smartb.im.core.organization.api.model.toOrganization
import city.smartb.im.core.organization.domain.model.Organization
import city.smartb.im.core.organization.domain.model.OrganizationId
import city.smartb.im.core.privilege.domain.model.RoleIdentifier
import city.smartb.im.infra.redis.CacheName
import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import f2.spring.exception.NotFoundException
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.stereotype.Service

@Service
class OrganizationCoreFinderService: CoreService(CacheName.Organization) {

    suspend fun getOrNull(id: OrganizationId): Organization? = query(id, "Error while fetching organization [$id]") {
        val client = keycloakClientProvider.get()
        try {
            client.group(id).toRepresentation().toOrganization()
        } catch (e: javax.ws.rs.NotFoundException) {
            null
        }
    }

    suspend fun get(id: OrganizationId): Organization {
        return getOrNull(id) ?: throw NotFoundException("Organization", id)
    }

    suspend fun page(
        ids: Collection<OrganizationId>? = null,
        search: String? = null,
        roles: Collection<RoleIdentifier>? = null,
        attributes: Map<String, String>? = null,
        withDisabled: Boolean = false,
        offset: OffsetPagination? = null,
    ): PageDTO<Organization> {
        val client = keycloakClientProvider.get()

        val compositeRoles = client.roles().list().associate { role ->
            val composites = client.role(role.name).realmRoleComposites.mapNotNull { it.name }
            role.name to composites.toList()
        }

        val groups = client.groups()
            .groups("", 0, Int.MAX_VALUE, false)
            .map(GroupRepresentation::toOrganization)
            .filter { organization ->
                organization.id.matches(ids)
                    && (withDisabled || organization.enabled)
                    && (attributes == null || attributes.all { (key, value) -> organization.attributes[key] == value })
                    && (search == null || organization.identifier.contains(search, true))
                    && (organization.roles.flatMap { compositeRoles[it].orEmpty() + it }.toSet().matches(roles))
            }.sortedByDescending(Organization::creationDate)

        return groups.page(offset)
    }
}
