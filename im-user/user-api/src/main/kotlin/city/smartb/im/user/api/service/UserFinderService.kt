package city.smartb.im.user.api.service

import city.smartb.im.api.config.ImKeycloakConfig
import city.smartb.im.user.domain.features.query.KeycloakUserGetFunction
import city.smartb.im.user.domain.features.query.KeycloakUserGetQuery
import city.smartb.im.user.domain.features.query.KeycloakUserPageFunction
import city.smartb.im.user.domain.features.query.KeycloakUserPageQuery
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.features.query.UserGetResult
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.domain.features.query.UserPageResult
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invokeWith
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val userTransformer: UserTransformer,
    private val imKeycloakConfig: ImKeycloakConfig,
    private val keycloakUserGetFunction: KeycloakUserGetFunction,
    private val keycloakUserPageFunction: KeycloakUserPageFunction
) {
    suspend fun userGet(query: UserGetQuery): UserGetResult {
        return query.toUserGetQuery().invokeWith(keycloakUserGetFunction)
            .item
            ?.let{userTransformer.toUser(it)}
            .let(::UserGetResult)
    }

    suspend fun userPage(query: UserPageQuery): UserPageResult {
        val result = query.toUserGetAllQuery().invokeWith(keycloakUserPageFunction).items
        val users = result.items.map { user ->
            userTransformer.toUser(user)
        }
        return UserPageResult(
            items = users,
            total = result.total
        )
    }

    private fun UserPageQuery.toUserGetAllQuery() = KeycloakUserPageQuery(
        groupId = organizationId,
        email = email,
        role = role,
        page = PagePagination(
            page = page,
            size = size
        ),
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )

    private fun UserGetQuery.toUserGetQuery() = KeycloakUserGetQuery(
        id = id,
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )
}
