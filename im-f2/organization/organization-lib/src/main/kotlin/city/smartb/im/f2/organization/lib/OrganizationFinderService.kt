package city.smartb.im.f2.organization.lib

import city.smartb.im.core.organization.api.OrganizationCoreFinderService
import city.smartb.im.core.organization.domain.model.Organization
import city.smartb.im.core.organization.domain.model.OrganizationId
import city.smartb.im.core.privilege.domain.model.RoleIdentifier
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import city.smartb.im.f2.organization.domain.model.OrganizationRef
import city.smartb.im.f2.organization.lib.model.toDTO
import city.smartb.im.f2.organization.lib.model.toOrganization
import city.smartb.im.f2.organization.lib.model.toRef
import city.smartb.im.f2.organization.lib.service.InseeHttpClient
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.cqrs.page.map
import org.springframework.stereotype.Service

@Service
class OrganizationFinderService(
    private val inseeHttpClient: InseeHttpClient?,
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val privilegeFinderService: PrivilegeFinderService
) {
    suspend fun getOrNull(id: OrganizationId): OrganizationDTOBase? {
        return organizationCoreFinderService.getOrNull(id)?.toDTOInternal()
    }

    suspend fun get(id: OrganizationId): OrganizationDTOBase {
        return organizationCoreFinderService.get(id).toDTOInternal()
    }

    suspend fun getFromInsee(siret: String): OrganizationDTOBase? {
        return try {
            inseeHttpClient?.getOrganizationBySiret(siret)
                ?.etablissement
                ?.toOrganization()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun page(
        search: String? = null,
        roles: Collection<RoleIdentifier>? = null,
        attributes: Map<String, String>? = null,
        withDisabled: Boolean = false,
        offset: OffsetPagination? = null,
    ): PageDTO<OrganizationDTOBase> {
        return organizationCoreFinderService.page(
            search = search,
            roles = roles,
            attributes = attributes,
            withDisabled = withDisabled,
            offset = offset,
        ).map { it.toDTOInternal() }
    }

    suspend fun listRefs(withDisabled: Boolean = false): List<OrganizationRef> {
        return organizationCoreFinderService.page(
            withDisabled = withDisabled,
        ).items.map(Organization::toRef)
    }

    private suspend fun Organization.toDTOInternal() = toDTO(
        getRole = privilegeFinderService::getRole
    )
}
