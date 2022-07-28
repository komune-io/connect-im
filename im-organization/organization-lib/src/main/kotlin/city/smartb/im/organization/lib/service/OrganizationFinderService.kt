package city.smartb.im.organization.lib.service

import city.smartb.im.api.auth.ImAuthenticationResolver
import city.smartb.im.organization.lib.model.toOrganization
import city.smartb.im.organization.lib.model.toOrganizationRef
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeResult
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetResult
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
import city.smartb.im.organization.domain.features.query.OrganizationPageResult
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllQuery
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllResult
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationDTO
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invoke
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageQuery
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.stereotype.Service

@Service
class OrganizationFinderService<MODEL: OrganizationDTO>(
    private val inseeHttpClient: InseeHttpClient,
    private val groupGetFunction: GroupGetFunction,
    private val groupPageFunction: GroupPageFunction,
    private val authenticationResolver: ImAuthenticationResolver,
    private val groupMapper: GroupMapper,
) {

    suspend fun organizationGet(
        query: OrganizationGetQuery,
        organizationMapper: OrganizationMapper<Organization, MODEL>
    ): OrganizationGetResult<MODEL> {
        return groupGetFunction.invoke(query.toGroupGetByIdQuery())
            .item
            ?.let { group -> groupMapper.toOrganization(group) }
            ?.let { org -> organizationMapper.from(org) }
            .let { dto ->
                OrganizationGetResult(dto)
            }
    }


    suspend fun organizationGetFromInsee(query: OrganizationGetFromInseeQuery): OrganizationGetFromInseeResult {
        val organizationDetails = try {
            inseeHttpClient.getOrganizationBySiret(query.siret)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return organizationDetails?.etablissement
            ?.toOrganization()
            .let(::OrganizationGetFromInseeResult)
    }

    suspend fun organizationPage(
        query: OrganizationPageQuery,
        organizationMapper: OrganizationMapper<Organization, MODEL>
    ): OrganizationPageResult<MODEL> {
        val result = groupPageFunction.invoke(query.toGroupPageQuery())
        val items = result.page.items.map { groupMapper.toOrganization(it)}.map { organizationMapper.from(it)}
        return OrganizationPageResult(
            items = items,
            total = result.page.total
        )
    }

    suspend fun organizationRefGetAll(query: OrganizationRefGetAllQuery): OrganizationRefGetAllResult {
        return groupPageFunction.invoke(query.toGroupPageQuery())
            .page
            .items
            .map(GroupModel::toOrganizationRef)
            .let(::OrganizationRefGetAllResult)
    }

    private suspend fun OrganizationRefGetAllQuery.toGroupPageQuery(): GroupPageQuery {
        val auth = authenticationResolver.getAuth()
        return GroupPageQuery(
            search = null,
            role = null,
            attributes = emptyMap(),
            withDisabled = withDisabled,
            page = PagePagination(
                page = null,
                size = null
            ),
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun OrganizationPageQuery.toGroupPageQuery(): GroupPageQuery {
        val auth = authenticationResolver.getAuth()
        return GroupPageQuery(
            search = search,
            role = role,
            attributes = attributes.orEmpty(),
            withDisabled = withDisabled,
            page = PagePagination(
                page = page,
                size = size
            ),
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun OrganizationGetQuery.toGroupGetByIdQuery(): GroupGetQuery {
        val auth = authenticationResolver.getAuth()
        return GroupGetQuery(
            id = id,
            realmId = auth.realmId,
            auth = auth
        )
    }
}
