package city.smartb.im.bdd.core.apikey.data

import city.smartb.im.core.client.domain.model.Client
import city.smartb.im.core.client.domain.model.ClientId
import city.smartb.im.core.client.domain.model.ClientIdentifier
import city.smartb.im.f2.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.ClientRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.client(keycloakClient: KeycloakClient) = AssertionClient(keycloakClient)

class AssertionClient(
    private val keycloakClient: KeycloakClient
): AssertionApiEntity<ClientRepresentation, ClientId, AssertionClient.ClientAssert>() {
    override suspend fun findById(id: ClientId): ClientRepresentation? = keycloakClient.client(id).toRepresentation()
    suspend fun findByIdentifier(identifier: ClientIdentifier): ClientRepresentation? = keycloakClient.getClientByIdentifier(identifier)
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
        ) = also {
            Assertions.assertThat(client.id).isEqualTo(id)
            Assertions.assertThat(client.clientId).isEqualTo(identifier)
        }

        fun matches(client: Client) = hasFields(
            id = client.id,
            identifier = client.identifier
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
