package io.komune.im.apikey.lib

import f2.dsl.cqrs.page.OffsetPagination
import f2.spring.exception.NotFoundException
import io.komune.im.apikey.domain.model.ApiKey
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.apikey.domain.model.ApiKeyIdentifier
import io.komune.im.apikey.domain.model.ApiKeyModel
import io.komune.im.apikey.domain.query.ApiKeyPageResult
import io.komune.im.apikey.lib.service.ApiKeyToDTOTransformer
import io.komune.im.apikey.lib.service.apiKeys
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.utils.page
import io.komune.im.core.organization.api.OrganizationCoreFilter
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.user.api.service.UserRepresentationTransformer
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.springframework.stereotype.Service

@Service
class ApiKeyFinderService(
    private val apiKeyToDTOTransformer: ApiKeyToDTOTransformer,
    private val keycloakClientProvider: KeycloakClientProvider,
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val userRepresentationTransformer: UserRepresentationTransformer
) {
    suspend fun getOrNullByIdentifier(id: ApiKeyIdentifier): ApiKey? {
        val user = getUserOfKeyOrNull(id)
        return user?.memberOf?.let { memberOf ->
            getOrNullInOrganization(id, memberOf)
        }
    }

    suspend fun getOrNullInOrganization(
        id: ApiKeyIdentifier,
        organizationId: OrganizationId
    ): ApiKey? {
        val organization = organizationCoreFinderService.getOrNull(organizationId)
        return organization
            ?.apiKeys()
            ?.firstOrNull { it.identifier == id }
            ?.let { apiKeyToDTOTransformer.transform(it) }
    }

    suspend fun getOrNull(id: ApiKeyId): ApiKey? {
        val user = getUserOfKey(id)
        val organization = organizationCoreFinderService.get(
            checkNotNull(user.memberOf) { "API key [$id] belongs to no organization" }
        )
        return organization.apiKeys()
            .firstOrNull { it.id == id }
            ?.let { apiKeyToDTOTransformer.transform(it) }
    }

    suspend fun get(id: ApiKeyId): ApiKey = getOrNull(id) ?: throw NotFoundException(name = "ApiKey", id = id)

    suspend fun getUserOfKey(id: ApiKeyId): UserModel =
        getUserOfKeyOrNull(id) ?: throw NotFoundException(name = "User of ApiKey", id = id)

    suspend fun getUserOfKeyOrNull(id: ApiKeyId): UserModel? {
        @Suppress("SwallowedException")
        return try {
            val client = keycloakClientProvider.getClient()
            client.client(id).serviceAccountUser
                .let { userRepresentationTransformer.transform(it) }
        } catch (e: JakartaNotFoundException) {
            null
        }
    }

    suspend fun page(
        filter: ApiKeyFilter = ApiKeyFilter(),
        offset: OffsetPagination? = null
    ): ApiKeyPageResult {
        val organizations = organizationCoreFinderService.page(
            OrganizationCoreFilter(
                ids = filter.organizationId?.let(::setOf),
                roles = filter.role?.let(::setOf),
                attributes = filter.attributes.orEmpty()
                    .mapValues { (_, expected) -> { attribute: String? -> attribute == expected } },
                isDisabledIncluded = filter.isDisabledIncluded,
            )
        ).items

        val page = organizations.flatMap { it.apiKeys() }
            .filteredByName(filter.search)
            .sortedByDescending { it.creationDate }
            .let { apiKeyToDTOTransformer.transform(it) }
            .page(offset)

        return ApiKeyPageResult(
            items = page.items,
            total = page.total
        )
    }

    private fun List<ApiKeyModel>.filteredByName(search: String?): List<ApiKeyModel> =
        search?.let { this.filter { apiKey -> apiKey.name.contains(search, true) } } ?: this
}

/** What [ApiKeyFinderService.page] keeps; a null criterion keeps everything. */
data class ApiKeyFilter(
    val search: String? = null,
    val organizationId: OrganizationId? = null,
    val role: String? = null,
    val attributes: Map<String, String>? = null,
    val isDisabledIncluded: Boolean = false,
)
