package io.komune.im.infra.keycloak.client

import f2.client.domain.RealmId
import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.commons.auth.currentAuth
import org.springframework.stereotype.Service

@Service
open class KeycloakClientProvider(
    private val authenticationResolver: ImAuthenticationProvider
) {

    suspend fun getClient(space: RealmId): KeycloakClient {
        val currentAuth = currentAuth()
        val auth = currentAuth ?: authenticationResolver.getAuth()
        val copy = auth.copy(space = space)
        val keycloakConnection = KeycloakClientBuilder.getConnection(copy)
        return keycloakConnection.forRealm(copy.space)
    }
    suspend fun getClient(): KeycloakClient {
        val currentAuth = currentAuth()
        val auth = currentAuth ?: authenticationResolver.getAuth()
        val keycloakConnection = KeycloakClientBuilder.getConnection(auth)
        return keycloakConnection.forRealm(auth.space)
    }

}
