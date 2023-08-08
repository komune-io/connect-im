package i2.test.it.client

import f2.dsl.fnc.invoke
import i2.keycloak.f2.client.query.ClientGetByClientIdentifierFunctionImpl
import i2.keycloak.f2.client.query.ClientGetFunctionImpl
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQuery
import i2.keycloak.f2.client.domain.features.query.ClientGetQuery
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.client
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class ClientGetByClientIdentifierFunctionImplTest: I2KeycloakTest() {

	private val masterClient = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(masterClient).realm().withTestRealm()


	@Test
	fun `should get client when exists`(): Unit = runBlocking {
		val clientIdentifier = UUID.randomUUID().toString()
		GivenKC(masterClient).client().withClient(realmId, clientIdentifier = clientIdentifier)


		val cmd = ClientGetByClientIdentifierQuery(
			clientIdentifier = clientIdentifier,
			realmId = realmId,
			auth = masterClient.auth
		)
		val result = ClientGetByClientIdentifierFunctionImpl().clientGetByClientIdentifierFunction().invoke(cmd)

		Assertions.assertThat(result.item).isNotNull
	}

	@Test
	fun `should not get client when not exists`(): Unit = runBlocking {
		val cmd = ClientGetQuery(
			id = "NOT_EXISTING_CLIENT",
			realmId = realmId,
			auth = masterClient.auth
		)
		val result = ClientGetFunctionImpl().clientGetFunction().invoke(cmd)

		Assertions.assertThat(result.item).isNull()
	}
}
