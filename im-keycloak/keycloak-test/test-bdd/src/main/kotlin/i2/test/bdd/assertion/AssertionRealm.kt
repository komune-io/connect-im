package i2.test.bdd.assertion

import javax.ws.rs.NotFoundException
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak

fun AssertionKC.realm(keycloak: Keycloak): RealmAssertion = RealmAssertion(keycloak)

class RealmAssertion(
	private val keycloak: Keycloak
) {
	companion object

	fun exist(id: String) {
		try {
			val realm = keycloak.realm(id).toRepresentation()
			Assertions.assertThat(realm).isNotNull
		} catch (e: NotFoundException) {
			Assertions.fail("Realm[${id} not found]", e)
		}
	}

	fun notExist(id: String) {
		try {
			keycloak.realm(id).toRepresentation()
			Assertions.fail("Realm[${id} exist]")
		} catch (e: NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}
}
