package i2.test.bdd.given

import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.buildRealmRepresentation
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.RealmRepresentation
import javax.ws.rs.NotFoundException

class GivenRealm(
	val client: KeycloakClient
) {

	companion object {
		const val REALM_TEST = "test"
	}

	fun withTestRealm(): RealmId {
		return withRealmId(REALM_TEST)
	}

	fun withRealmId(id: RealmId): String {
		try {
			val realm = getRealmRepresentation(id)
			Assertions.assertThat(realm).isNotNull
		} catch (e: NotFoundException) {
			create(id)
		}
		return id
	}

	private fun create(id: String): RealmRepresentation? {
		return try {
			val realm = buildRealmRepresentation(
				realm = id
			)
			client.realms().create(realm)
			getRealmRepresentation(id)
		} catch (e: NotFoundException) {
			Assertions.fail("Error initializing realm [${id}]")
		}
	}
	private fun getRealmRepresentation(id: String) = client.keycloak.realm(id).toRepresentation()

}

fun GivenKC.realm() = GivenRealm(this.client)
