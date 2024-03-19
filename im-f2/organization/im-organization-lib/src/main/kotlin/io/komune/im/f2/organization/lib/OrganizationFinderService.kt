package io.komune.im.f2.organization.lib

import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.organization.domain.model.OrganizationModel
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationDTO
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.organization.lib.model.toDTO
import io.komune.im.f2.organization.lib.model.toOrganization
import io.komune.im.f2.organization.lib.model.toRef
import io.komune.im.f2.organization.lib.service.InseeHttpClient
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
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
    suspend fun getOrNull(id: OrganizationId): Organization? {
        return organizationCoreFinderService.getOrNull(id)?.toDTOInternal()
    }

    suspend fun get(id: OrganizationId): Organization {
        return organizationCoreFinderService.get(id).toDTOInternal()
    }

    suspend fun getFromInsee(siret: String): Organization? {
        return try {
            inseeHttpClient?.getOrganizationBySiret(siret)
                ?.etablissement
                ?.toOrganization()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun page(
        name: String? = null,
        roles: Collection<RoleIdentifier>? = null,
        attributes: Map<String, String>? = null,
        status: Collection<OrganizationStatus>? = null,
        withDisabled: Boolean = false,
        offset: OffsetPagination? = null,
    ): PageDTO<Organization> {
        val attributesFilters = attributes.orEmpty().mapValues { (_, filter) -> ({ attribute: String? -> attribute == filter }) }
        val additionalAttributesFilters = listOfNotNull(
            status?.let { OrganizationDTO::status.name to ({ attribute: String? -> attribute in status.map(
                OrganizationStatus::name) }) }
        ).toMap()

        return organizationCoreFinderService.page(
            identifier = name,
            roles = roles,
            attributes = attributesFilters + additionalAttributesFilters,
            withDisabled = withDisabled,
            offset = offset,
        ).map { it.toDTOInternal() }
    }

    suspend fun listRefs(withDisabled: Boolean = false): List<OrganizationRef> {
        return organizationCoreFinderService.page(
            withDisabled = withDisabled,
        ).items.map(OrganizationModel::toRef)
    }

    private suspend fun OrganizationModel.toDTOInternal() = toDTO(
        getRole = privilegeFinderService::getRole
    )
}
