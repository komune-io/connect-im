//package i2.test.it.client
//
//import f2.dsl.fnc.invoke
//import i2.keycloak.f2.client.command.ClientCreateFunctionImpl
//import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
//import i2.test.bdd.assertion.AssertionKC
//import i2.test.bdd.assertion.client
//import i2.test.bdd.given.GivenKC
//import i2.test.bdd.given.auth
//import i2.test.bdd.given.realm
//import i2.test.bdd.testcontainers.I2KeycloakTest
//import kotlinx.coroutines.runBlocking
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//import java.util.UUID
//
//class ClientCreateFunctionImplTest : I2KeycloakTest() {
//
//	val masterRealmClient = GivenKC().auth().withMasterRealmClient()
//
//	@Test
//	fun `should create a new realm`(): Unit = runBlocking {
//		GivenKC().realm().withTestRealm()
//
//		val clientIdentifier = "client-${UUID.randomUUID()}"
//		val cmd = ClientCreateCommand(
//			clientIdentifier = clientIdentifier,
//			realmId = "test",
//			auth = masterRealmClient.auth
//		)
//		val event = ClientCreateFunctionImpl().clientCreateFunction().invoke(cmd)
//		Assertions.assertThat(event.id).isNotNull
//		AssertionKC.client(masterRealmClient.keycloak).exists("test", clientIdentifier)
//	}
//}
