package io.komune.im.bdd.core.apikey.data

import io.komune.im.bdd.core.organization.data.AssertionOrganizationRef
import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.core.client.domain.model.ClientModel
import io.komune.im.infra.keycloak.client.KeycloakClient
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.ClientRepresentation
import org.slf4j.LoggerFactory
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.client(keycloakClient: KeycloakClient) = AssertionClient(keycloakClient)

class AssertionClient(
    private val keycloakClient: KeycloakClient
): AssertionApiEntity<ClientRepresentation, ClientId, AssertionClient.ClientAssert>() {

    private val logger = LoggerFactory.getLogger(AssertionOrganizationRef::class.java)

    override suspend fun findById(id: ClientId): ClientRepresentation? = try {
        keycloakClient.client(id).toRepresentation()
    } catch (e: JakartaNotFoundException) {
        logger.debug("Client not found with id: $id", e)
        null
    }

    fun findByIdentifier(identifier: ClientIdentifier): ClientRepresentation? = try {
        keycloakClient.getClientByIdentifier(identifier)
    } catch (e: JakartaNotFoundException) {
        logger.debug("Client not found with identifier: $identifier", e)
        null
    }

    override suspend fun assertThat(entity: ClientRepresentation) = ClientAssert(entity)

    suspend fun assertThatIdentifier(identifier: ClientIdentifier): ClientAssert {
        val entity = checkNotNull(findByIdentifier(identifier)) { "Client [$identifier] not found" }
        return assertThat(entity)
    }

    inner class ClientAssert(
        private val client: ClientRepresentation
    ) {
        /** What the asserted entity holds, as fields to compare. */
        fun actual() = ClientFields(
            id = client.id,
            identifier = client.clientId,
            isPublicClient = client.isPublicClient,
            isDirectAccessGrantsEnabled = client.isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled = client.isServiceAccountsEnabled,
            isStandardFlowEnabled = client.isStandardFlowEnabled,
        )

        /** Asserts the entity holds [expected], its own fields by default. */
        @IgnorableReturnValue
        fun hasFields(expected: ClientFields = actual()) = also {
            Assertions.assertThat(client.id).isEqualTo(expected.id)
            Assertions.assertThat(client.clientId).isEqualTo(expected.identifier)
            Assertions.assertThat(client.isPublicClient).isEqualTo(expected.isPublicClient)
            Assertions.assertThat(client.isDirectAccessGrantsEnabled).isEqualTo(expected.isDirectAccessGrantsEnabled)
            Assertions.assertThat(client.isServiceAccountsEnabled).isEqualTo(expected.isServiceAccountsEnabled)
            Assertions.assertThat(client.isStandardFlowEnabled).isEqualTo(expected.isStandardFlowEnabled)
        }

        /** Compares the entity with its own fields as [change] rewrites them. */
        @IgnorableReturnValue
        inline fun hasFields(change: (ClientFields) -> ClientFields) = hasFields(change(actual()))

        /** Asserts the entity holds the fields of the given model. */
        @IgnorableReturnValue
        fun matches(client: ClientModel) = hasFields { fields -> fields.copy(
            id = client.id,
            identifier = client.identifier,
            isPublicClient = client.isPublicClient,
            isDirectAccessGrantsEnabled = client.isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled = client.isServiceAccountsEnabled,
            isStandardFlowEnabled = client.isStandardFlowEnabled
        ) }

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

/** The fields a client assertion compares. */
data class ClientFields(
    val id: ClientId,
    val identifier: ClientIdentifier,
    val isPublicClient: Boolean,
    val isDirectAccessGrantsEnabled: Boolean,
    val isServiceAccountsEnabled: Boolean,
    val isStandardFlowEnabled: Boolean,
)
