package city.smartb.im.infra.keycloak.client

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.model.ImMessage
import city.smartb.im.commons.model.RealmId
import org.springframework.stereotype.Service

@Service
open class KeycloakClientProvider(
    private val authenticationResolver: ImAuthenticationProvider
) {
    var connection: KeycloakClientBuilder.KeycloakClientConnection? = null
    private val cache = mutableMapOf<RealmId, KeycloakClient>()

    open suspend fun getFor(message: ImMessage): KeycloakClient {
        return getFor(message.realmId)
    }

    open suspend fun getFor(realmId: RealmId?): KeycloakClient {
        val auth = authenticationResolver.getAuth()
        val keycloakConnection = connection
            ?: KeycloakClientBuilder.openConnection(auth)

        return cache.getOrPut(auth.space) {
            keycloakConnection.forRealm(auth.space)
        }
    }
}
