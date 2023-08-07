package i2.test.it.role

import f2.dsl.fnc.invoke
import i2.keycloak.f2.role.command.RoleAddCompositesFunctionImpl
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.role
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.role.roleAddCompositesCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.role
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class RoleAddCompositesFunctionImplTest: I2KeycloakTest() {

	private val masterClient = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(masterClient).realm().withTestRealm()

	private val roleId = GivenKC(masterClient).role().withRole(realmId, UUID.randomUUID().toString())

	@Test
	fun `should add composites to a realm role`(): Unit = runBlocking {
		val givenRole = GivenKC(masterClient).role()
		val compositeRoles = listOf(
			givenRole.withRole(realmId, UUID.randomUUID().toString()),
			givenRole.withRole(realmId, UUID.randomUUID().toString()),
			givenRole.withRole(realmId, UUID.randomUUID().toString())
		)

		val cmd = DataTest.roleAddCompositesCommand(
			auth = masterClient.auth,
			realmId = realmId,
			roleName = roleId,
			composites = compositeRoles
		)
		RoleAddCompositesFunctionImpl().roleAddCompositesFunction().invoke(cmd)

		AssertionKC.role(masterClient.keycloak).assertThat(realmId, cmd.roleName).hasFields(
			composites = cmd.composites
		)
	}

	@Test
	fun `should not add composites to a realm role when one of the composites does not exist`(): Unit = runBlocking {
		val cmd = DataTest.roleAddCompositesCommand(
			auth = masterClient.auth,
			realmId = realmId,
			roleName = roleId,
			composites = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())
		)

		Assertions.assertThatThrownBy { runBlocking {
			RoleAddCompositesFunctionImpl().roleAddCompositesFunction().invoke(cmd)
		}}.isNotNull
	}

	@Test
	fun `should not add composites to a realm role when the role does not exist`(): Unit = runBlocking {
		val cmd = DataTest.roleAddCompositesCommand(
			auth = masterClient.auth,
			realmId = realmId,
			composites = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())
		)

		Assertions.assertThatThrownBy { runBlocking {
			RoleAddCompositesFunctionImpl().roleAddCompositesFunction().invoke(cmd)
		}}.isNotNull
	}
}
