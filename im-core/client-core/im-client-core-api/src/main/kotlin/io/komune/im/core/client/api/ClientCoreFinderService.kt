package io.komune.im.core.client.api

import f2.spring.exception.NotFoundException
import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.core.client.api.model.toClient
import io.komune.im.core.client.domain.model.ClientModel
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientCoreFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
) {
    private val logger = LoggerFactory.getLogger(ClientCoreFinderService::class.java)

    suspend fun getOrNull(id: ClientId): ClientModel? {
        val keycloakClient = keycloakClientProvider.getClient()
        try {
            return keycloakClient.client(id)
                .toRepresentation()
                .toClient()
        } catch (e: JakartaNotFoundException) {
            logger.debug("Client not found with id: $id", e)
            return null
        }
    }

    suspend fun get(id: ClientId): ClientModel {
        return getOrNull(id) ?: throw NotFoundException("Client", id)
    }

    suspend fun getByIdentifierOrNull(identifier: ClientIdentifier): ClientModel? {
        val keycloakClient = keycloakClientProvider.getClient()
        try {
            return keycloakClient.getClientByIdentifier(identifier)?.toClient()
        } catch (e: JakartaNotFoundException) {
            logger.debug("Client not found with identifier: $identifier", e)
            return null
        }
    }

    suspend fun getByIdentifier(identifier: ClientIdentifier): ClientModel {
        return getByIdentifierOrNull(identifier) ?: throw NotFoundException("Client with identifier", identifier)
    }

    suspend fun listClientRoles(id: ClientId): List<String> {
        val keycloakClient = keycloakClientProvider.getClient()
        try {
            return keycloakClient.client(id)
                .roles()
                .list()
                .map { it.name }
        } catch (e: JakartaNotFoundException) {
            throw NotFoundException("Client", id).initCause(e)
        }
    }
}
