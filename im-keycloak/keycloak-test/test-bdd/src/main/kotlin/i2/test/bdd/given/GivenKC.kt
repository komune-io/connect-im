package i2.test.bdd.given

import city.smartb.im.infra.keycloak.client.KeycloakClient

class GivenKC(
    val client: KeycloakClient = GivenAuth().withMasterRealmClient(),
) {
	companion object
}
