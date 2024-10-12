package io.komune.im.apikey.lib

import io.komune.im.apikey.domain.model.ApiKey
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.apikey.domain.model.ApiKeyModel
import io.komune.im.apikey.domain.query.ApiKeyPageResult
import io.komune.im.apikey.lib.service.ApiKeyToDTOTransformer
import io.komune.im.apikey.lib.service.apiKeys
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.utils.page
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.user.api.service.UserRepresentationTransformer
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import f2.dsl.cqrs.page.OffsetPagination
import f2.spring.exception.NotFoundException
import io.komune.im.apikey.domain.model.ApiKeyIdentifier
import org.springframework.stereotype.Service
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

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
            getOrNullByIdentifierAndOrganizationId(id, memberOf)
        }
    }
    suspend fun getOrNullByIdentifierAndOrganizationId(
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
        val organization = organizationCoreFinderService.get(user.memberOf!!)
        return organization.apiKeys()
            .firstOrNull { it.id == id }
            ?.let { apiKeyToDTOTransformer.transform(it) }
    }

    suspend fun get(id: ApiKeyId): ApiKey {
        return getOrNull(id) ?: throw NotFoundException("ApiKey", id)
    }

    suspend fun getUserOfKey(id: ApiKeyId): UserModel {
        return getUserOfKeyOrNull(id) ?: throw NotFoundException("User of ApiKey", id)
    }

    suspend fun getUserOfKeyOrNull(id: ApiKeyId): UserModel? {
        return try {
            val client = keycloakClientProvider.get()
             client.client(id).serviceAccountUser
                .let { userRepresentationTransformer.transform(it) }
        } catch (e: JakartaNotFoundException) {
            null
        }
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
            attributes = attributes.orEmpty().mapValues { (_, filter) -> ({ attribute: String? -> attribute == filter }) },
            withDisabled = withDisabled ?: false
        ).items

        val page = organizations.flatMap { it.apiKeys() }
            .filteredByName(search)
            .sortedByDescending { it.creationDate }
            .let { apiKeyToDTOTransformer.transform(it) }
            .page(offset)

        return ApiKeyPageResult(
            items = page.items,
            total = page.total
        )
    }

    private fun List<ApiKeyModel>.filteredByName(search: String?): List<ApiKeyModel> {
        return search?.let { this.filter { it.name.contains(search, true) } } ?: this
    }
}
