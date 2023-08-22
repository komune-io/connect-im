package i2.test.bdd.given

import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.buildRealmRepresentation
import i2.keycloak.realm.client.config.realmsResource
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.RealmRepresentation
import javax.ws.rs.NotFoundException

class GivenRealm(
	val client: AuthRealmClient
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
			val realm = client.buildRealmRepresentation(
				realm = id
			)
			client.realmsResource().create(realm)
			getRealmRepresentation(id)
		} catch (e: NotFoundException) {
			Assertions.fail("Error initializing realm [${id}]")
		}
	}
	private fun getRealmRepresentation(id: String) = client.keycloak.realm(id).toRepresentation()

}

fun GivenKC.realm() = GivenRealm(this.client)
