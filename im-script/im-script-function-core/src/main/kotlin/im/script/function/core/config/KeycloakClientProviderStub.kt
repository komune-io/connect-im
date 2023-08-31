package im.script.function.core.config

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientBuilder
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class KeycloakClientProviderStub(
    private val authenticationResolver: ImAuthenticationProvider
): KeycloakClientProvider(authenticationResolver) {
    private val cache = mutableMapOf<String, KeycloakClientCache>()

    override suspend fun get(): KeycloakClient {
        val auth = authenticationResolver.getAuth()
        val clientCache = cache.getOrPut("${auth.serverUrl} ${auth.clientId}") {
            KeycloakClientCache(KeycloakClientBuilder.openConnection(auth))
        }
        return clientCache.clients.getOrPut(auth.space) {
            clientCache.connection.forRealm(auth.space)
        }
    }

    private data class KeycloakClientCache(
        val connection: KeycloakClientBuilder.KeycloakClientConnection,
        val clients: MutableMap<RealmId?, KeycloakClient> = mutableMapOf()
    )
}
