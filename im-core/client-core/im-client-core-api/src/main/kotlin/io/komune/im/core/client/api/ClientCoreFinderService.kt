package io.komune.im.core.client.api

import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.core.client.api.model.toClient
import io.komune.im.core.client.domain.model.ClientModel
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import f2.spring.exception.NotFoundException
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.springframework.stereotype.Service

@Service
class ClientCoreFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
) {
    suspend fun getOrNull(id: ClientId): ClientModel? {
        val keycloakClient = keycloakClientProvider.get()

        return try {
            keycloakClient.client(id)
                .toRepresentation()
                .toClient()
        } catch (e: JakartaNotFoundException) {
            null
        }
    }

    suspend fun get(id: ClientId): ClientModel {
        return getOrNull(id) ?: throw NotFoundException("Client", id)
    }

    suspend fun getByIdentifierOrNull(identifier: ClientIdentifier): ClientModel? {
        val keycloakClient = keycloakClientProvider.get()

        return try {
            keycloakClient.getClientByIdentifier(identifier)?.toClient()
        } catch (e: JakartaNotFoundException) {
            null
        }
    }

    suspend fun getByIdentifier(identifier: ClientIdentifier): ClientModel {
        return getByIdentifierOrNull(identifier) ?: throw NotFoundException("Client with identifier", identifier)
    }

    suspend fun listClientRoles(id: ClientId): List<String> {
        val keycloakClient = keycloakClientProvider.get()

        return try {
            keycloakClient.client(id)
                .roles()
                .list()
                .map { it.name }
        } catch (e: JakartaNotFoundException) {
            throw NotFoundException("Client", id)
        }
    }
}
