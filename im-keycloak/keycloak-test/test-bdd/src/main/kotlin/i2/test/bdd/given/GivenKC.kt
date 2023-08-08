package i2.test.bdd.given

import i2.keycloak.realm.client.config.AuthRealmClient

class GivenKC(
	val client: AuthRealmClient = GivenAuth().withMasterRealmClient(),
) {
	companion object
}
