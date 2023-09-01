package city.smartb.im.organization.lib

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
import city.smartb.im.organization.domain.features.query.OrganizationPageResult
import city.smartb.im.organization.domain.features.query.OrganizationRefListQuery
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.organization.domain.model.OrganizationRef
import city.smartb.im.organization.lib.model.toOrganization
import city.smartb.im.organization.lib.model.toOrganizationRef
import city.smartb.im.organization.lib.service.InseeHttpClient
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import f2.spring.exception.NotFoundException
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageQuery
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.stereotype.Service

@Service
class OrganizationFinderService(
    private val inseeHttpClient: InseeHttpClient?,
    private val groupGetFunction: GroupGetFunction,
    private val groupPageFunction: GroupPageFunction,
    private val authenticationResolver: ImAuthenticationProvider,
    private val redisCache: RedisCache,
) {

    suspend fun getOrNull(id: OrganizationId): Organization? = redisCache.getFromCacheOr(CacheName.Organization, id) {
        val auth = authenticationResolver.getAuth()
        GroupGetQuery(
            id = id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(groupGetFunction)
            .item
            ?.let(GroupModel::toOrganization)
    }

    suspend fun get(id: OrganizationId): Organization {
        return getOrNull(id) ?: throw NotFoundException("Organization", id)
    }

    suspend fun getFromInsee(query: OrganizationGetFromInseeQuery): Organization? {
        return try {
            inseeHttpClient?.getOrganizationBySiret(query.siret)
                ?.etablissement
                ?.toOrganization()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun page(
        query: OrganizationPageQuery,
    ): OrganizationPageResult {
        val result = groupPageFunction.invoke(query.toGroupPageQuery())
        val items = result.page.items.map(GroupModel::toOrganization)
        return OrganizationPageResult(
            items = items,
            total = result.page.total
        )
    }

    suspend fun listRefs(query: OrganizationRefListQuery): List<OrganizationRef> {
        return groupPageFunction.invoke(query.toGroupPageQuery())
            .page
            .items
            .map(GroupModel::toOrganizationRef)
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
