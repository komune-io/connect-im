package io.komune.im.f2.space.lib

import io.komune.im.api.config.PageDefault
import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.core.commons.CoreService
import io.komune.im.f2.space.domain.model.Space
import io.komune.im.f2.space.domain.query.SpacePageResult
import io.komune.im.f2.space.lib.model.toSpace
import io.komune.im.infra.redis.CacheName
import f2.dsl.cqrs.page.PagePagination
import f2.spring.exception.NotFoundException
import org.keycloak.representations.idm.RealmRepresentation
import org.springframework.stereotype.Service
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

@Service
class SpaceFinderService: CoreService(CacheName.Space) {

    suspend fun getOrNull(id: SpaceIdentifier): Space? = query(id) {
        val client = keycloakClientProvider.get()

        try {
            client.realm(id)
                .toRepresentation()
                .toSpace()
        } catch (e: JakartaNotFoundException) {
            null
        }
    }

    suspend fun get(id: SpaceIdentifier): Space {
        return getOrNull(id) ?: throw NotFoundException("Space", id)
    }

    suspend fun page(
        search: String? = null,
        page: Int?,
        size: Int?
    ): SpacePageResult {
        val client = keycloakClientProvider.get()
        val actualPage = page ?: PageDefault.PAGE_NUMBER
        val actualSize = size ?: PageDefault.PAGE_SIZE

        val realms = client.realms().findAll()
            .sortedBy { it.id }
            .toMutableList()
            .apply {
                if (search != null) {
                    val searchLowercase = search.lowercase()
                    removeIf { it.id.lowercase().contains(searchLowercase) }
                }
            }

        return SpacePageResult(
            items = realms.chunked(actualSize)
                .getOrNull(actualPage)
                .orEmpty()
                .map(RealmRepresentation::toSpace),
            pagination = PagePagination(size = size, page = page),
            total = realms.size
        )
    }
}
