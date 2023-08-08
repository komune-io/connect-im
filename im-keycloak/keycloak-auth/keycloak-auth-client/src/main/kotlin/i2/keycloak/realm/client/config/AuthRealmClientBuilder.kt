package i2.keycloak.realm.client.config

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmException
import i2.keycloak.master.domain.AuthRealmPassword
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder

class AuthRealmClientBuilder {

	companion object {
		const val CONNECTION_POOL_SIZE = 10
	}

	fun build(auth: AuthRealm): AuthRealmClient {
		return when (auth) {
			is AuthRealmPassword -> init(auth)
			is AuthRealmClientSecret -> init(auth)
			else -> throw AuthRealmException("Invalid AuthRealm type[${auth::class.simpleName}]")
		}
	}

	private fun init(auth: AuthRealmClientSecret): AuthRealmClient {
		val keycloak = KeycloakBuilder.builder()
			.serverUrl(auth.serverUrl)
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.realm(auth.realmId)
			.clientId(auth.clientId)
			.clientSecret(auth.clientSecret)
			.resteasyClient(ResteasyClientBuilder().connectionPoolSize(CONNECTION_POOL_SIZE).build())
			.build()
		val realm = keycloak.realm(auth.realmId)
		return AuthRealmClient(keycloak, realm, auth)
	}

	private fun init(auth: AuthRealmPassword): AuthRealmClient {
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
		return AuthRealmClient(keycloak, realm, auth)
	}
}
