package io.komune.im.script.core.config

import f2.client.domain.RealmId
import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.commons.auth.currentAuth
import io.komune.im.infra.keycloak.client.KeycloakClient
import io.komune.im.infra.keycloak.client.KeycloakClientBuilder
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class KeycloakClientProviderStub(
    private val authenticationResolver: ImAuthenticationProvider
): KeycloakClientProvider(authenticationResolver) {
    private val cache = mutableMapOf<String, KeycloakClientCache>()

    override suspend fun get(): KeycloakClient {
        val auth = currentAuth()!!
        val clientCache = cache.getOrPut("${auth.master.serverUrl} ${auth.master.clientId}") {
            KeycloakClientCache(KeycloakClientBuilder.openConnection(auth))
        }
        return clientCache.clients.getOrPut(auth.space) {
            clientCache.connection.forRealm(auth.space)
        }
    }

    override fun reset() {
        cache.values.forEach {
            it.connection.keycloak.close()
        }
        cache.clear()
    }

    private data class KeycloakClientCache(
        val connection: KeycloakClientBuilder.KeycloakClientConnection,
        val clients: MutableMap<RealmId?, KeycloakClient> = mutableMapOf()
    )
}
