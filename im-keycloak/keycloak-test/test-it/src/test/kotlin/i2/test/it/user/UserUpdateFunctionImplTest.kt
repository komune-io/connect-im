package i2.test.it.user

import f2.dsl.fnc.invoke
import i2.keycloak.f2.commons.domain.error.I2Exception
import i2.keycloak.f2.user.command.UserUpdateFunctionImpl
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.user
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.user.userUpdateCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.group
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class UserUpdateFunctionImplTest: I2KeycloakTest() {

	private val clientMaster = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(clientMaster).realm().withTestRealm()

	@Test
	fun `should update existing user`(): Unit = runBlocking {
		val userId = GivenKC(clientMaster).user().withUser(realmId, UUID.randomUUID().toString())
		val groupId = GivenKC(clientMaster).group().withGroup(realmId, UUID.randomUUID().toString())

		val updateCommand = DataTest.userUpdateCommand(
			userId = userId,
			realmId = realmId,
			auth = clientMaster.auth,
			firstname = "Sandra",
			lastname = "Geffroi",
			attributes = mapOf("memberOf" to groupId)
		)
		val updatedUserId = UserUpdateFunctionImpl().userUpdateFunction().invoke(updateCommand).id

		AssertionKC.user(clientMaster.keycloak).assertThat(realmId, updatedUserId).hasFields(
			userId = userId,
			firstname = updateCommand.firstname,
			lastname = updateCommand.lastname,
			attributes = updateCommand.attributes
		)
	}

	@Test
	fun `should throw I2Exception if user doesn't exist`() = runBlocking {
		val updateCommand = DataTest.userUpdateCommand(realmId = realmId, auth = clientMaster.auth)

		try {
			UserUpdateFunctionImpl().userUpdateFunction().invoke(updateCommand)
			Assertions.fail("User[${updateCommand.userId}] should not exist")
		} catch (e: I2Exception) {}
	}
}
