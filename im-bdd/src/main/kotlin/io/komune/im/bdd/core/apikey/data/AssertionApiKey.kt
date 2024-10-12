package io.komune.im.bdd.core.apikey.data

import io.komune.im.apikey.domain.model.ApiKey
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.apikey.domain.model.ApiKeyIdentifier
import io.komune.im.apikey.domain.model.ApiKeyModel
import io.komune.im.apikey.lib.service.ORGANIZATION_FIELD_API_KEYS
import io.komune.im.commons.utils.parseJson
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

fun AssertionBdd.apiKey(client: KeycloakClient) = AssertionApiKey(client)

class AssertionApiKey(
    private val client: KeycloakClient
): AssertionApiEntity<ApiKeyModel, ApiKeyId, AssertionApiKey.ApiKeyAssert>() {
    override suspend fun findById(id: ApiKeyId): ApiKeyModel? {
        try {
            val organizationId = client.client(id)
                .serviceAccountUser
                .attributes
                ?.get("memberOf")
                ?.firstOrNull()
                ?: return null

            val organization = client.group(organizationId).toRepresentation()
            val apiKeys = organization.attributes?.get(ORGANIZATION_FIELD_API_KEYS)?.firstOrNull()?.parseJson<Array<ApiKeyModel>>()
            return apiKeys?.firstOrNull { it.id == id }
        } catch (e: JakartaNotFoundException) {
            return null
        }
    }
    override suspend fun assertThat(entity: ApiKeyModel) = ApiKeyAssert(entity)

    inner class ApiKeyAssert(
        private val apiKey: ApiKeyModel
    ) {
        fun hasFields(
            id: ApiKeyId = apiKey.id,
            identifier: ApiKeyIdentifier = apiKey.identifier,
            name: String = apiKey.name,
            roles: List<String>? = apiKey.roles,
        ) = also {
            Assertions.assertThat(apiKey.id).isEqualTo(id)
            Assertions.assertThat(apiKey.identifier).isEqualTo(identifier)
            Assertions.assertThat(apiKey.name).isEqualTo(name)
            Assertions.assertThat(roles).isEqualTo(roles)
        }

        fun matches(apiKey: ApiKey) = hasFields(
            id = apiKey.id,
            identifier = apiKey.identifier,
            name = apiKey.name,
            roles = apiKey.roles.map(Role::identifier),
        )
    }
}
