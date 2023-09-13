package city.smartb.im.core.organization.api

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.utils.matches
import city.smartb.im.commons.utils.page
import city.smartb.im.core.commons.CoreService
import city.smartb.im.core.organization.api.model.toOrganization
import city.smartb.im.core.organization.domain.model.OrganizationModel
import city.smartb.im.infra.redis.CacheName
import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import f2.spring.exception.NotFoundException
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.stereotype.Service

@Service
class OrganizationCoreFinderService: CoreService(CacheName.Organization) {

    suspend fun getOrNull(id: OrganizationId): OrganizationModel? = query(id, "Error while fetching organization [$id]") {
        val client = keycloakClientProvider.get()
        try {
            client.group(id).toRepresentation().toOrganization()
        } catch (e: javax.ws.rs.NotFoundException) {
            null
        }
    }

    suspend fun get(id: OrganizationId): OrganizationModel {
        return getOrNull(id) ?: throw NotFoundException("Organization", id)
    }

    suspend fun page(
        ids: Collection<OrganizationId>? = null,
        identifier: String? = null,
        roles: Collection<RoleIdentifier>? = null,
        attributes: Map<String, (String?) -> Boolean>? = null,
        withDisabled: Boolean = false,
        offset: OffsetPagination? = null,
    ): PageDTO<OrganizationModel> {
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
                    && (attributes == null || attributes.all { (key, match) -> match(organization.attributes[key]) })
                    && (identifier == null || organization.identifier.contains(identifier, true))
                    && (organization.roles.flatMap { compositeRoles[it].orEmpty() + it }.toSet().matches(roles))
            }.sortedByDescending(OrganizationModel::creationDate)

        return groups.page(offset)
    }
}
