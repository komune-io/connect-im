package city.smartb.im.infra.keycloak.client

import city.smartb.im.infra.keycloak.AuthRealm
import city.smartb.im.infra.keycloak.AuthRealmClientSecret
import city.smartb.im.infra.keycloak.AuthRealmException
import city.smartb.im.infra.keycloak.AuthRealmPassword
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder

object KeycloakClientBuilder {
    private const val CONNECTION_POOL_SIZE = 10

	fun build(auth: AuthRealm): KeycloakClient {
		return when (auth) {
			is AuthRealmPassword -> init(auth)
			is AuthRealmClientSecret -> init(auth)
			else -> throw AuthRealmException("Invalid AuthRealm type[${auth::class.simpleName}]")
		}
	}

	private fun init(auth: AuthRealmClientSecret): KeycloakClient {
		val keycloak = KeycloakBuilder.builder()
			.serverUrl(auth.serverUrl)
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.realm(auth.realmId)
			.clientId(auth.clientId)
			.clientSecret(auth.clientSecret)
			.resteasyClient(ResteasyClientBuilder().connectionPoolSize(CONNECTION_POOL_SIZE).build())
			.build()
		val realm = keycloak.realm(auth.realmId)
		return KeycloakClient(keycloak, realm, auth)
	}

	private fun init(auth: AuthRealmPassword): KeycloakClient {
		val keycloak = KeycloakBuilder.builder()
			.serverUrl(auth.serverUrl)
			.grantType(OAuth2Constants.PASSWORD)
			.realm(auth.realmId)
			.clientId(auth.clientId)
			.username(auth.username)
			.password(auth.password)
			.resteasyClient(ResteasyClientBuilder().connectionPoolSize(CONNECTION_POOL_SIZE).build())
			.build()
		val realm = keycloak.realm(auth.realmId)
		return KeycloakClient(keycloak, realm, auth)
	}
}
