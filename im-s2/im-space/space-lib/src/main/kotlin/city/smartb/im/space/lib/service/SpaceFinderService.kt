package city.smartb.im.space.lib.service

import city.smartb.im.api.config.PageDefault
import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.space.domain.features.query.SpaceGetQuery
import city.smartb.im.space.domain.features.query.SpaceGetResult
import city.smartb.im.space.domain.features.query.SpacePageQuery
import city.smartb.im.space.domain.features.query.SpacePageResult
import city.smartb.im.space.domain.model.Space
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.query.RealmGetFunction
import i2.keycloak.f2.realm.domain.features.query.RealmGetQuery
import i2.keycloak.f2.realm.domain.features.query.RealmListFunction
import i2.keycloak.f2.realm.domain.features.query.RealmListQuery
import org.springframework.stereotype.Service

@Service
open class SpaceFinderService(
    private val realmGetFunction: RealmGetFunction,
    private val realmListFunction: RealmListFunction,
    private val authenticationResolver: ImAuthenticationProvider,
    private val redisCache: RedisCache,
) {

    suspend fun spaceGet(
        query: SpaceGetQuery,
    ): SpaceGetResult = redisCache.getFormCacheOr(CacheName.Space, query.id) {
        val auth = authenticationResolver.getAuth()
        val result = RealmGetQuery(query.id, auth).invokeWith(realmGetFunction)
        SpaceGetResult(result.item?.let {
            Space(it.id, it.name)
        })
    }

    suspend fun spacePage(
        query: SpacePageQuery,
    ): SpacePageResult {
        val size = query.size ?: PageDefault.PAGE_SIZE
        val page = query.page ?: PageDefault.PAGE_NUMBER
        val auth = authenticationResolver.getAuth()
        val result = RealmListQuery(auth).invokeWith(realmListFunction)
        val items = result.items
            .chunked(size)
            .getOrElse(page) { emptyList() }
                .map { it.toSpace() }
        return SpacePageResult(
            items = items,
            pagination = PagePagination(size = query.size, page = query.page),
            total = result.total
        )
    }

    private fun RealmModel.toSpace(): Space {
        return Space(this.id, this.name)
    }
}
