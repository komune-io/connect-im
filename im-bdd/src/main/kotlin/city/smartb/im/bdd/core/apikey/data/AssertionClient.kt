package city.smartb.im.bdd.core.apikey.data

import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.ClientIdentifier
import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.core.client.domain.model.ClientModel
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.ClientRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.client(keycloakClient: KeycloakClient) = AssertionClient(keycloakClient)

class AssertionClient(
    private val keycloakClient: KeycloakClient
): AssertionApiEntity<ClientRepresentation, ClientId, AssertionClient.ClientAssert>() {
    override suspend fun findById(id: ClientId): ClientRepresentation? = try {
        keycloakClient.client(id).toRepresentation()
    } catch (e: javax.ws.rs.NotFoundException) {
        null
    }

    fun findByIdentifier(identifier: ClientIdentifier): ClientRepresentation? = try {
        keycloakClient.getClientByIdentifier(identifier)
    } catch (e: javax.ws.rs.NotFoundException) {
        null
    }

    override suspend fun assertThat(entity: ClientRepresentation) = ClientAssert(entity)

    suspend fun assertThatIdentifier(identifier: ClientIdentifier): ClientAssert {
        val entity = findByIdentifier(identifier)
        Assertions.assertThat(entity).isNotNull
        return assertThat(entity!!)
    }

    inner class ClientAssert(
        private val client: ClientRepresentation
    ) {
        fun hasFields(
            id: ClientId = client.id,
            identifier: ClientIdentifier = client.clientId,
            isPublicClient: Boolean = client.isPublicClient,
            isDirectAccessGrantsEnabled: Boolean = client.isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled: Boolean = client.isServiceAccountsEnabled,
            isStandardFlowEnabled: Boolean = client.isStandardFlowEnabled
        ) = also {
            Assertions.assertThat(client.id).isEqualTo(id)
            Assertions.assertThat(client.clientId).isEqualTo(identifier)
            Assertions.assertThat(client.isPublicClient).isEqualTo(isPublicClient)
            Assertions.assertThat(client.isDirectAccessGrantsEnabled).isEqualTo(isDirectAccessGrantsEnabled)
            Assertions.assertThat(client.isServiceAccountsEnabled).isEqualTo(isServiceAccountsEnabled)
            Assertions.assertThat(client.isStandardFlowEnabled).isEqualTo(isStandardFlowEnabled)
        }

        fun matches(client: ClientModel) = hasFields(
            id = client.id,
            identifier = client.identifier,
            isPublicClient = client.isPublicClient,
            isDirectAccessGrantsEnabled = client.isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled = client.isServiceAccountsEnabled,
            isStandardFlowEnabled = client.isStandardFlowEnabled
        )

        fun hasClientRoles(clientId: ClientId, roles: Collection<PrivilegeIdentifier>) {
            Assertions.assertThat(client.isServiceAccountsEnabled).isTrue

            val serviceAccountUser = keycloakClient.client(client.id).serviceAccountUser
            val clientRoles = keycloakClient.user(serviceAccountUser.id)
                .roles()
                .clientLevel(clientId)
                .listAll()
                .map { it.name }
            Assertions.assertThat(clientRoles).containsExactlyInAnyOrderElementsOf(roles)
        }
    }
}
