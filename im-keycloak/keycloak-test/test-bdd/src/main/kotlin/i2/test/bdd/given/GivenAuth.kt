package i2.test.bdd.given

import city.smartb.im.commons.model.AuthRealmClientSecret
import city.smartb.im.commons.model.AuthRealmPassword
import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientBuilder
import i2.test.bdd.config.KeycloakConfig

class GivenAuth {

	fun withMasterRealmClient(realm: RealmId = "master"): KeycloakClient {
		val auth = AuthRealmPassword(
			serverUrl = KeycloakConfig.url,
			clientId = KeycloakConfig.Admin.clientId,
			username = KeycloakConfig.Admin.username,
			password = KeycloakConfig.Admin.password,
			realmId = realm,
			redirectUrl = "http://localhost:3000",
		)
		return KeycloakClientBuilder.openConnection(auth).forRealm(realm)
	}

	fun withRealmClient(realm: RealmId): KeycloakClient {
		val auth = AuthRealmClientSecret(
			serverUrl = KeycloakConfig.url,
			clientId = "admin-cli",
			clientSecret = "test",
			realmId = realm,
			redirectUrl = "http://localhost:3000",
		)
		return KeycloakClientBuilder.openConnection(auth).forRealm(realm)
	}
}

fun GivenKC.auth(): GivenAuth = GivenAuth()
