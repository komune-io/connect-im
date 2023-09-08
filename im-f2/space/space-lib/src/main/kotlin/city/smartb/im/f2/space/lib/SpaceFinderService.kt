package city.smartb.im.f2.space.lib

import city.smartb.im.api.config.PageDefault
import city.smartb.im.commons.model.SpaceIdentifier
import city.smartb.im.core.commons.CoreService
import city.smartb.im.f2.space.domain.model.Space
import city.smartb.im.f2.space.domain.query.SpacePageResult
import city.smartb.im.f2.space.lib.model.toSpace
import city.smartb.im.infra.redis.CacheName
import f2.dsl.cqrs.page.PagePagination
import f2.spring.exception.NotFoundException
import org.keycloak.representations.idm.RealmRepresentation
import org.springframework.stereotype.Service

@Service
class SpaceFinderService: CoreService(CacheName.Space) {

    suspend fun getOrNull(id: SpaceIdentifier): Space? = query(id) {
        val client = keycloakClientProvider.get()

        try {
            client.realm(id)
                .toRepresentation()
                .toSpace()
        } catch (e: javax.ws.rs.NotFoundException) {
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
