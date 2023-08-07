package i2.test.it.user

import f2.dsl.fnc.invoke
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.user.userCreateCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class UserGetByEmailFunctionImplTest: I2KeycloakTest() {

	private val client = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(client).realm().withTestRealm()

	@Autowired
	lateinit var userGetByEmailFunction: UserGetByEmailFunction

	@Test
	fun `should get user when user with email exists`(): Unit = runBlocking {
		val createCommand = DataTest.userCreateCommand(
			realmId = realmId,
			auth = client.auth,
			email = "${UUID.randomUUID()}@email.com"
		)
		GivenKC(client).user().withUser(createCommand)

		val cmd = UserGetByEmailQuery(
			email = createCommand.email,
			realmId = realmId,
			auth = client.auth
		)
		val result = userGetByEmailFunction.invoke(cmd)

		Assertions.assertThat(result.item).isNotNull
	}

	@Test
	fun `should not get user when no user with email exists`(): Unit = runBlocking {
		val createCommand = DataTest.userCreateCommand(
			realmId = realmId,
			auth = client.auth,
			email = "${UUID.randomUUID()}@email.com"
		)
		GivenKC(client).user().withUser(createCommand)

		val cmd = UserGetByEmailQuery(
			email = "invalid-email@email.com",
			realmId = realmId,
			auth = client.auth
		)
		val result = userGetByEmailFunction.invoke(cmd)

		Assertions.assertThat(result.item).isNull()
	}
}
