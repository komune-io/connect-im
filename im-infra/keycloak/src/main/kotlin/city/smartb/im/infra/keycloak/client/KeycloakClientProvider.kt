package city.smartb.im.infra.keycloak.client

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.auth.AuthenticationProvider
import city.smartb.im.commons.model.ImMessage
import city.smartb.im.commons.model.RealmId
import org.springframework.stereotype.Service

@Service
class KeycloakClientProvider(
    private val authenticationResolver: ImAuthenticationProvider
) {
    private val cache = mutableMapOf<String, KeycloakClientCache>()

    suspend fun getFor(message: ImMessage): KeycloakClient {
        return getFor(message.realmId)
    }

    suspend fun getFor(realmId: RealmId?): KeycloakClient {
        val issuer = AuthenticationProvider.getIssuer()
        val clientCache = cache.getOrPut(issuer) {
            KeycloakClientCache(KeycloakClientBuilder.openConnection(authenticationResolver.getAuth()))
        }
        return clientCache.clients.getOrPut(realmId) {
            clientCache.connection.forRealm(realmId)
        }
    }

    private data class KeycloakClientCache(
        val connection: KeycloakClientBuilder.KeycloakClientConnection,
        val clients: MutableMap<RealmId?, KeycloakClient> = mutableMapOf()
    )
}
