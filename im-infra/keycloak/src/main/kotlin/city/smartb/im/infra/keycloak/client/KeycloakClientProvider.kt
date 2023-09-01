package city.smartb.im.infra.keycloak.client

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.auth.currentAuth
import city.smartb.im.commons.model.RealmId
import org.springframework.stereotype.Service

@Service
open class KeycloakClientProvider(
    private val authenticationResolver: ImAuthenticationProvider
) {
    private var connection: KeycloakClientBuilder.KeycloakClientConnection? = null
    private val cache = mutableMapOf<RealmId, KeycloakClient>()

    open suspend fun get(): KeycloakClient {
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
