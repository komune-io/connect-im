package city.smartb.im.user.lib.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.user.domain.features.query.KeycloakUserGetByEmailFunction
import city.smartb.im.user.domain.features.query.KeycloakUserGetByEmailQuery
import city.smartb.im.user.domain.features.query.KeycloakUserGetFunction
import city.smartb.im.user.domain.features.query.KeycloakUserGetQuery
import city.smartb.im.user.domain.features.query.KeycloakUserPageFunction
import city.smartb.im.user.domain.features.query.KeycloakUserPageQuery
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.domain.features.query.UserPageResult
import city.smartb.im.user.domain.model.User
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invokeWith
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val userTransformer: UserTransformer,
    private val keycloakUserGetFunction: KeycloakUserGetFunction,
    private val keycloakUserGetByEmailFunction: KeycloakUserGetByEmailFunction,
    private val keycloakUserPageFunction: KeycloakUserPageFunction,
    private val authenticationResolver: ImAuthenticationProvider,
    private val redisCache: RedisCache,
) {
    suspend fun userGet(query: UserGetQuery): User? = redisCache.getFormCacheOr(CacheName.User, query.id) {
        val auth = authenticationResolver.getAuth()
        return KeycloakUserGetQuery(
            id = query.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(keycloakUserGetFunction)
            .item
            ?.let { userTransformer.toUser(it) }
    }

    suspend fun userGetByEmail(email: String): User? {
        val auth = authenticationResolver.getAuth()
        return KeycloakUserGetByEmailQuery(
            email = email,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(keycloakUserGetByEmailFunction)
            .item
            ?.let { userTransformer.toUser(it) }
    }

    suspend fun userPage(query: UserPageQuery): UserPageResult {
        val result = query.toUserPageQuery().invokeWith(keycloakUserPageFunction).items
        val users = result.items.map { user ->
            userTransformer.toUser(user)
        }
        return UserPageResult(
            items = users,
            total = result.total
        )
    }

    private suspend fun UserPageQuery.toUserPageQuery(): KeycloakUserPageQuery {
        val auth = authenticationResolver.getAuth()
        return KeycloakUserPageQuery(
            groupId = organizationId,
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
}
