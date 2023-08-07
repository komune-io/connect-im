package i2.test.it.user

import f2.dsl.fnc.invoke
import i2.keycloak.f2.user.command.UserDisableFunctionImpl
import i2.keycloak.f2.user.domain.features.command.UserDisableCommand
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.user
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.UUID

internal class UserDisableFunctionImplTest: I2KeycloakTest() {

	val client = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(client).realm().withTestRealm()
	val userId = GivenKC(client).user().withUser(realmId, UUID.randomUUID().toString())

	@Test
	fun `should disable user`(): Unit = runBlocking {

		val cmd = UserDisableCommand(
			id = userId,
			realmId = realmId,
			auth = client.auth
		)
		UserDisableFunctionImpl().userDisableFunction().invoke(cmd)


		AssertionKC.user(client.keycloak).isDisabled(realmId, userId)
		//TODO Check we can't login with the user
	}
}
