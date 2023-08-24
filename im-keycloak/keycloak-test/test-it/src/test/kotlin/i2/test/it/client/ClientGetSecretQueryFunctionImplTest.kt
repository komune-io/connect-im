//package i2.test.it.client
//
//import f2.dsl.fnc.invoke
//import i2.keycloak.f2.client.query.ClientGetSecretFunctionImpl
//import i2.keycloak.f2.client.domain.features.query.ClientGetSecretQuery
//import i2.keycloak.f2.commons.domain.error.I2Exception
//import i2.test.bdd.given.GivenKC
//import i2.test.bdd.given.auth
//import i2.test.bdd.given.client
//import i2.test.bdd.given.realm
//import i2.test.bdd.testcontainers.I2KeycloakTest
//import java.util.UUID
//import kotlinx.coroutines.runBlocking
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//
//class ClientGetSecretQueryFunctionImplTest: I2KeycloakTest() {
//
//    private val masterClient = GivenKC().auth().withMasterRealmClient()
//    private val realmId = GivenKC(masterClient).realm().withRealmId(UUID.randomUUID().toString())
//
//    private val clientId = GivenKC(masterClient).client().withClient(realmId)
//
//    @Test
//    fun `should get secret of client when exists`(): Unit = runBlocking {
//        val cmd = ClientGetSecretQuery(
//            clientId = clientId,
//            realmId = realmId,
//            auth = masterClient.auth
//        )
//        val result = ClientGetSecretFunctionImpl().clientGetSecretFunction().invoke(cmd)
//
//        Assertions.assertThat(result.secret).isNull()
//    }
//
//    @Test
//    fun `should not get secret of client when not exists`() {
//        val cmd = ClientGetSecretQuery(
//            clientId = "NOT_EXISTING_CLIENT",
//            realmId = realmId,
//            auth = masterClient.auth
//        )
//
//        Assertions.assertThatThrownBy { runBlocking {
//            ClientGetSecretFunctionImpl().clientGetSecretFunction().invoke(cmd)
//        }}.isInstanceOf(I2Exception::class.java)
//    }
//}
