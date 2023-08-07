package i2.test.it.realm

import f2.dsl.fnc.invoke
import i2.keycloak.f2.realm.query.RealmGetFunctionImpl
import i2.keycloak.f2.realm.domain.features.query.RealmGetQuery
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class RealmGetFunctionImplTest: I2KeycloakTest() {

	val masterClient = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(masterClient).realm().withTestRealm()

	@Test
	fun `should return a not null realm when exists`(): Unit = runBlocking {
		val cmd = RealmGetQuery(
			id = realmId,
			authRealm = masterClient.auth
		)

		val found = RealmGetFunctionImpl().realmGetFunction().invoke(cmd)
		Assertions.assertThat(found.item).isNotNull

	}

	@Test
	fun `should return null realm when not exists`(): Unit = runBlocking {
		val cmd = RealmGetQuery(
			id = "NOT_EXISTING_REALM",
			authRealm = masterClient.auth
		)

		val found = RealmGetFunctionImpl().realmGetFunction().invoke(cmd)
		Assertions.assertThat(found.item).isNull()
	}
}
