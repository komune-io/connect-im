package city.smartb.im.organization.lib.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeResult
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetResult
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
import city.smartb.im.organization.domain.features.query.OrganizationPageResult
import city.smartb.im.organization.domain.features.query.OrganizationRefListQuery
import city.smartb.im.organization.domain.features.query.OrganizationRefListResult
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationDTO
import city.smartb.im.organization.lib.model.toOrganization
import city.smartb.im.organization.lib.model.toOrganizationRef
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invoke
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageQuery
import i2.keycloak.f2.group.domain.model.GroupModel

open class OrganizationFinderService<MODEL : OrganizationDTO>(
    private val inseeHttpClient: InseeHttpClient?,
    private val groupGetFunction: GroupGetFunction,
    private val groupPageFunction: GroupPageFunction,
    private val authenticationResolver: ImAuthenticationProvider,
    private val groupMapper: GroupMapper,
    private val redisCache: RedisCache,
) {

    suspend fun organizationGet(
        query: OrganizationGetQuery,
        organizationMapper: OrganizationMapper<Organization, MODEL>,
    ): OrganizationGetResult<MODEL> = redisCache.getFromCacheOr(CacheName.Organization, query.id) {
        groupGetFunction.invoke(query.toGroupGetByIdQuery())
            .item
            ?.let { group -> groupMapper.toOrganization(group) }
    }
        ?.let { org -> organizationMapper.mapModel(org) }
        .let { dto ->
            OrganizationGetResult(dto)
        }


    suspend fun organizationGetFromInsee(query: OrganizationGetFromInseeQuery): OrganizationGetFromInseeResult {
        val organizationDetails = try {
            inseeHttpClient?.getOrganizationBySiret(query.siret)
        } catch (e: Exception) {
            null
        }
        return organizationDetails?.etablissement
            ?.toOrganization()
            .let(::OrganizationGetFromInseeResult)
    }

    suspend fun organizationPage(
        query: OrganizationPageQuery,
        organizationMapper: OrganizationMapper<Organization, MODEL>,
    ): OrganizationPageResult<MODEL> {
        val result = groupPageFunction.invoke(query.toGroupPageQuery())
        val items = result.page.items.map { groupMapper.toOrganization(it) }.map { organizationMapper.mapModel(it) }
        return OrganizationPageResult(
            items = items,
            total = result.page.total
        )
    }

    suspend fun organizationRefList(query: OrganizationRefListQuery): OrganizationRefListResult {
        return groupPageFunction.invoke(query.toGroupPageQuery())
            .page
            .items
            .map(GroupModel::toOrganizationRef)
            .let(::OrganizationRefListResult)
    }

    private suspend fun OrganizationRefListQuery.toGroupPageQuery(): GroupPageQuery {
        val auth = authenticationResolver.getAuth()
        return GroupPageQuery(
            search = null,
            roles = null,
            attributes = emptyMap(),
            withDisabled = withDisabled,
            page = PagePagination(
                page = null,
                size = null
            ),
            realmId = auth.space,
            auth = auth
        )
    }

    private suspend fun OrganizationPageQuery.toGroupPageQuery(): GroupPageQuery {
        val auth = authenticationResolver.getAuth()
        val allRoles = buildList {
            roles?.let(::addAll)
            role?.let(::add)
        }
        return GroupPageQuery(
            search = search,
            roles = allRoles,
            attributes = attributes.orEmpty(),
            withDisabled = withDisabled ?: false,
            page = PagePagination(
                page = page,
                size = size
            ),
            realmId = auth.space,
            auth = auth
        )
    }

    private suspend fun OrganizationGetQuery.toGroupGetByIdQuery(): GroupGetQuery {
        val auth = authenticationResolver.getAuth()
        return GroupGetQuery(
            id = id,
            realmId = auth.space,
            auth = auth
        )
    }
}
