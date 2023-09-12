package city.smartb.im.apikey.lib

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.apikey.domain.query.ApiKeyGetResult
import city.smartb.im.apikey.domain.query.ApiKeyPageResult
import city.smartb.im.apikey.lib.service.apiKeys
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.utils.page
import city.smartb.im.core.organization.api.OrganizationCoreFinderService
import city.smartb.im.core.user.api.service.UserRepresentationTransformer
import city.smartb.im.core.user.domain.model.UserModel
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import f2.dsl.cqrs.page.OffsetPagination
import org.springframework.stereotype.Service

@Service
class ApiKeyFinderService(
    private val keycloakClientProvider: KeycloakClientProvider,
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val userRepresentationTransformer: UserRepresentationTransformer
) {
    suspend fun get(id: ApiKeyId): ApiKeyGetResult {
        val user = getUserOfKey(id)
        val organization = organizationCoreFinderService.get(user.memberOf!!)
        return ApiKeyGetResult(
            item = organization.apiKeys().firstOrNull { it.id == id }
        )
    }

    suspend fun getUserOfKey(id: ApiKeyId): UserModel {
        val client = keycloakClientProvider.get()
        return client.client(id).serviceAccountUser
            .let { userRepresentationTransformer.transform(it) }
    }

    suspend fun page(
        search: String? = null,
        organizationId: OrganizationId? = null,
        role: String? = null,
        attributes: Map<String, String>? = null,
        withDisabled: Boolean? = false,
        offset: OffsetPagination? = null
    ): ApiKeyPageResult {
        val organizations = organizationCoreFinderService.page(
            ids = organizationId?.let(::setOf),
            roles = role?.let(::setOf),
            attributes = attributes,
            withDisabled = withDisabled ?: false
        ).items

        val page = organizations.flatMap { it.apiKeys() }
            .filteredByName(search)
            .sortedByDescending { it.creationDate }
            .page(offset)

        return ApiKeyPageResult(
            items = page.items,
            total = page.total
        )
    }

    private fun List<ApiKey>.filteredByName(search: String?): List<ApiKey> {
        return search?.let { this.filter { it.name.contains(search, true) } } ?: this
    }
}
