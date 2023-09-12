package city.smartb.im.core.client.api

import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.ClientIdentifier
import city.smartb.im.core.client.api.model.toClient
import city.smartb.im.core.client.domain.model.ClientModel
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import f2.spring.exception.NotFoundException
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
        } catch (e: javax.ws.rs.NotFoundException) {
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
        } catch (e: javax.ws.rs.NotFoundException) {
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
        } catch (e: javax.ws.rs.NotFoundException) {
            throw NotFoundException("Client", id)
        }
    }
}
