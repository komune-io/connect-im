package io.komune.im.bdd.core.apikey.data

import io.komune.im.bdd.core.organization.data.AssertionOrganizationRef
import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.core.client.domain.model.ClientModel
import io.komune.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.ClientRepresentation
import org.slf4j.LoggerFactory
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

fun AssertionBdd.client(keycloakClient: KeycloakClient) = AssertionClient(keycloakClient)

class AssertionClient(
    private val keycloakClient: KeycloakClient
): AssertionApiEntity<ClientRepresentation, ClientId, AssertionClient.ClientAssert>() {

    private val logger = LoggerFactory.getLogger(AssertionOrganizationRef::class.java)

    override suspend fun findById(id: ClientId): ClientRepresentation? = try {
        keycloakClient.client(id).toRepresentation()
    } catch (e: JakartaNotFoundException) {
        logger.debug("Client not found with id: $id")
        null
    }

    fun findByIdentifier(identifier: ClientIdentifier): ClientRepresentation? = try {
        keycloakClient.getClientByIdentifier(identifier)
    } catch (e: JakartaNotFoundException) {
        logger.debug("Client not found with identifier: $identifier")
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
