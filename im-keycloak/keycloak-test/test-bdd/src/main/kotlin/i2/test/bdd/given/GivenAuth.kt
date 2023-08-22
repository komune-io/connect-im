package i2.test.bdd.given

import city.smartb.im.infra.keycloak.AuthRealmClientSecret
import city.smartb.im.infra.keycloak.AuthRealmPassword
import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.test.bdd.config.KeycloakConfig

class GivenAuth {

	fun withMasterRealmClient(realm: RealmId = "master"): AuthRealmClient {
		val auth = AuthRealmPassword(
			serverUrl = KeycloakConfig.url,
			clientId = KeycloakConfig.Admin.clientId,
			username = KeycloakConfig.Admin.username,
			password = KeycloakConfig.Admin.password,
			realmId = realm,
			redirectUrl = "http://localhost:3000",
		)
		return AuthRealmClientBuilder().build(auth)
	}

	fun withRealmClient(realm: RealmId): AuthRealmClient {
		val auth = AuthRealmClientSecret(
			serverUrl = KeycloakConfig.url,
			clientId = "admin-cli",
			clientSecret = "test",
			realmId = realm,
			redirectUrl = "http://localhost:3000",
		)
		return AuthRealmClientBuilder().build(auth)
	}
}

fun GivenKC.auth(): GivenAuth = GivenAuth()
