package io.komune.im.f2.organization.lib

import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.cqrs.page.map
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.core.organization.api.OrganizationCoreFilter
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

    suspend fun getRefOrNull(id: OrganizationId): OrganizationRef? {
        return organizationCoreFinderService.getOrNull(id)?.toRef()
    }

    suspend fun get(id: OrganizationId): Organization {
        return organizationCoreFinderService.get(id).toDTOInternal()
    }

    @Suppress("SwallowedException", "TooGenericExceptionCaught")
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
        filter: OrganizationFilter = OrganizationFilter(),
        offset: OffsetPagination? = null,
    ): PageDTO<Organization> {
        val attributesFilters = filter.attributes
            .orEmpty()
            .mapValues { (_, expected) -> ({ attribute: String? -> attribute == expected }) }
        val additionalAttributesFilters = listOfNotNull(
            filter.status?.let { status ->
                val names = status.map(OrganizationStatus::name)
                OrganizationDTO::status.name to ({ attribute: String? -> attribute in names })
            }
        ).toMap()

        return organizationCoreFinderService.page(
            OrganizationCoreFilter(
                name = filter.name,
                roles = filter.roles,
                attributes = attributesFilters + additionalAttributesFilters,
                withDisabled = filter.withDisabled,
            ),
            offset = offset,
        ).map { it.toDTOInternal() }
    }

    suspend fun listRefs(withDisabled: Boolean = false): List<OrganizationRef> {
        return organizationCoreFinderService.page(
            OrganizationCoreFilter(withDisabled = withDisabled),
        ).items.map(OrganizationModel::toRef)
    }

    private suspend fun OrganizationModel.toDTOInternal() = toDTO(
        getRole = privilegeFinderService::getRole
    )
}

/** What [OrganizationFinderService.page] keeps; a null criterion keeps everything. */
data class OrganizationFilter(
    val name: String? = null,
    val roles: Collection<RoleIdentifier>? = null,
    val attributes: Map<String, String>? = null,
    val status: Collection<OrganizationStatus>? = null,
    val withDisabled: Boolean = false,
)
