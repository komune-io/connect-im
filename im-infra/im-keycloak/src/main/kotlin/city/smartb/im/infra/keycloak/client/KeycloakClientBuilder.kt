package city.smartb.im.infra.keycloak.client

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.AuthRealmClientSecret
import city.smartb.im.commons.model.AuthRealmPassword
import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.AuthRealmException
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder

object KeycloakClientBuilder {
    private const val CONNECTION_POOL_SIZE = 10

    fun openConnection(auth: AuthRealm): KeycloakClientConnection {
        return when (auth) {
            is AuthRealmPassword -> openConnection(auth) {
                grantType(OAuth2Constants.PASSWORD)
                username(auth.username)
                password(auth.password)
            }
            is AuthRealmClientSecret -> openConnection(auth) {
                grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                clientSecret(auth.clientSecret)
            }
            else -> throw AuthRealmException("Invalid AuthRealm type[${auth::class.simpleName}]")
        }
    }

    private fun openConnection(auth: AuthRealm, configure: KeycloakBuilder.() -> KeycloakBuilder): KeycloakClientConnection {
        val keycloak = KeycloakBuilder.builder()
            .serverUrl(auth.serverUrl)
            .realm(auth.realmId)
            .clientId(auth.clientId)
            .resteasyClient(ResteasyClientBuilder().connectionPoolSize(CONNECTION_POOL_SIZE).build())
            .configure()
            .build()
        return KeycloakClientConnection(keycloak, auth)
    }

    class KeycloakClientConnection(
        val keycloak: Keycloak,
        val auth: AuthRealm
    ) {
        fun forRealm(realmId: RealmId?) = KeycloakClient(keycloak, auth, realmId ?: auth.space)
        fun forAuthedRealm() = forRealm(null)
    }
}
