package io.komune.im.infra.keycloak.client

import f2.client.domain.AuthRealmClientSecret
import f2.client.domain.AuthRealmPassword
import f2.client.domain.RealmId
import io.komune.im.commons.model.AuthSubRealm
import io.komune.im.infra.keycloak.AuthRealmException
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.slf4j.LoggerFactory

object KeycloakClientBuilder {
    private const val CONNECTION_POOL_SIZE = 10
    private val logger = LoggerFactory.getLogger(KeycloakClientBuilder::class.java)

    fun openConnection(auth: AuthSubRealm): KeycloakClientConnection {
        logger.debug("Open Keycloak connection for $auth")
        val master = auth.master
        return when (master) {
            is AuthRealmPassword -> openConnection(auth) {
                grantType(OAuth2Constants.PASSWORD)
                username(master.username)
                password(master.password)
            }
            is AuthRealmClientSecret -> openConnection(auth) {
                grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                clientSecret(master.clientSecret)
            }
            else -> throw AuthRealmException("Invalid AuthRealm type[${auth::class.simpleName}]")
        }
    }

    private fun openConnection(auth: AuthSubRealm, configure: KeycloakBuilder.() -> KeycloakBuilder): KeycloakClientConnection {
        val resteasyClientBuilder = ResteasyClientBuilder.newBuilder() as ResteasyClientBuilder
        val keycloak = KeycloakBuilder.builder()
            .serverUrl(auth.master.serverUrl)
            .realm(auth.master.realmId)
            .clientId(auth.master.clientId)
            .resteasyClient(resteasyClientBuilder.connectionPoolSize(CONNECTION_POOL_SIZE).build())
            .configure()
            .build()
        return KeycloakClientConnection(keycloak, auth)
    }

    class KeycloakClientConnection(
        val keycloak: Keycloak,
        val auth: AuthSubRealm
    ) {
        fun forRealm(realmId: RealmId?) = KeycloakClient(keycloak, auth.master, realmId ?: auth.space)
        fun forAuthedRealm() = forRealm(null)
    }
}
