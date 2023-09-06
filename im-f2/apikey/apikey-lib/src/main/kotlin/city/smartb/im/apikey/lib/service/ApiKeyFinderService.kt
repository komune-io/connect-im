package city.smartb.im.apikey.lib.service

import city.smartb.im.apikey.domain.features.query.ApiKeyGetQuery
import city.smartb.im.apikey.domain.features.query.ApiKeyGetResult
import city.smartb.im.apikey.domain.features.query.ApiKeyPageQuery
import city.smartb.im.apikey.domain.features.query.ApiKeyPageResult
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.commons.utils.page
import city.smartb.im.core.organization.api.OrganizationCoreFinderService
import org.springframework.stereotype.Service

@Service
class ApiKeyFinderService(
    private val organizationCoreFinderService: OrganizationCoreFinderService
) {
    suspend fun apikeyGet(query: ApiKeyGetQuery): ApiKeyGetResult {
        val organization = organizationCoreFinderService.get(query.organizationId)
        return ApiKeyGetResult(
            item = organization.apiKeys().firstOrNull { it.id == query.id }
        )
    }

    suspend fun apikeyPage(
        query: ApiKeyPageQuery,
    ): ApiKeyPageResult {
        val organizations = organizationCoreFinderService.page(
            ids = query.organizationId?.let(::setOf),
            roles = query.role?.let(::setOf),
            attributes = query.attributes,
            withDisabled = query.withDisabled ?: false
        ).items

        val page = organizations.flatMap { it.apiKeys() }
            .filteredByName(query.search)
            .sortedByDescending { it.creationDate }
            .page(query.offset, query.limit)

        return ApiKeyPageResult(
            items = page.items,
            total = page.total
        )
    }

    private fun List<ApiKey>.filteredByName(search: String?): List<ApiKey> {
        return search?.let { this.filter { it.name.contains(search, true) } } ?: this
    }
}
