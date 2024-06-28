package io.komune.im.infra.keycloak.client

import f2.client.ktor.http.plugin.model.RealmId
import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.commons.auth.currentAuth
import org.springframework.stereotype.Service

@Service
open class KeycloakClientProvider(
    private val authenticationResolver: ImAuthenticationProvider
) {
    private var connection: KeycloakClientBuilder.KeycloakClientConnection? = null
    private val cache = mutableMapOf<RealmId, KeycloakClient>()

    suspend fun get(): KeycloakClient {
        val auth = currentAuth() ?: authenticationResolver.getAuth()
        val keycloakConnection = connection
            ?: KeycloakClientBuilder.openConnection(auth)
        return cache.getOrPut(auth.space) {
            keycloakConnection.forRealm(auth.space)
        }
    }

    open suspend fun reset() {
        connection?.keycloak?.close()
        connection = null
        cache.clear()
    }
}
