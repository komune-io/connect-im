//package i2.test.it.realm
//
//import f2.dsl.fnc.invoke
//import i2.keycloak.f2.realm.command.RealmCreateFunctionImpl
//import i2.keycloak.f2.realm.domain.features.command.RealmCreateCommand
//import i2.test.bdd.assertion.AssertionKC
//import i2.test.bdd.assertion.realm
//import i2.test.bdd.given.GivenKC
//import i2.test.bdd.given.auth
//import i2.test.bdd.testcontainers.I2KeycloakTest
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.Test
//import java.util.UUID
//
//class RealmCreateFunctionImplTest : I2KeycloakTest() {
//
//	val masterClient = GivenKC().auth().withMasterRealmClient()
//
//	@Test
//	fun `should failed realm`(): Unit = runBlocking {
//		val id = "test-${UUID.randomUUID()}"
//		AssertionKC.realm(masterClient.keycloak).notExist(id)
//	}
//
//	@Test
//	fun `should create a new realm`(): Unit = runBlocking {
//		val id = "test-${UUID.randomUUID()}"
//		val cmd = RealmCreateCommand(
//			id = id,
//			theme = null,
//			locale = null,
//			smtpServer = null,
//			masterRealmAuth = masterClient.auth
//		)
//
//		RealmCreateFunctionImpl().realmCreateFunction().invoke(cmd)
//		AssertionKC.realm(masterClient.keycloak).exist(id)
//	}
//}
