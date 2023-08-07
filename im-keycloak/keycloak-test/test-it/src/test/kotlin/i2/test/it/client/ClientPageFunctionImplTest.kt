package i2.test.it.client

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invoke
import i2.keycloak.f2.client.query.ClientPageFunctionImpl
import i2.keycloak.f2.client.domain.features.query.ClientPageQuery
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.client
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ClientPageFunctionImplTest: I2KeycloakTest() {

	private val masterClient = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(masterClient).realm().withRealmId(UUID.randomUUID().toString())

	@Test
	fun `should get page of client`(): Unit = runBlocking {
		val existingClients = masterClient.clients(realmId).findAll()

		val expectedClients = (0..6).map {
			GivenKC(masterClient).client().withClient(realmId)
		}

		val cmd = ClientPageQuery(
			realmId = realmId,
			auth = masterClient.auth,
			page = PagePagination(
				page = 0,
				size = 5
			)
		)
		val result = ClientPageFunctionImpl().clientPageFunction().invoke(cmd)

		Assertions.assertThat(result.page.items).hasSize(cmd.page.size!!)
		Assertions.assertThat(result.page.total).isEqualTo(expectedClients.size + existingClients.size.toLong())
	}

	@Test
	fun `should get last page of client`(): Unit = runBlocking {
		val existingClients = masterClient.clients(realmId).findAll()

		val expectedClients = (0..6).map {
			GivenKC(masterClient).client().withClient(realmId)
		}

		val totalClientCount = existingClients.size + expectedClients.size
		val lastPageSize = 2
		val pageSize = totalClientCount - lastPageSize

		val cmd = ClientPageQuery(
			realmId = realmId,
			auth = masterClient.auth,
			page = PagePagination(
				page = 1,
				size = pageSize
			)
		)
		val result = ClientPageFunctionImpl().clientPageFunction().invoke(cmd)

		Assertions.assertThat(result.page.items).hasSize(lastPageSize)
		Assertions.assertThat(result.page.total).isEqualTo(expectedClients.size + existingClients.size.toLong())
	}

	@Test
	fun `should return empty page of clients`(): Unit = runBlocking {
		val existingClients = masterClient.clients(realmId).findAll()

		val cmd = ClientPageQuery(
			realmId = realmId,
			auth = masterClient.auth,
			page = PagePagination(
				page = 10,
				size = existingClients.size
			)
		)
		val result = ClientPageFunctionImpl().clientPageFunction().invoke(cmd)

		Assertions.assertThat(result.page.items).hasSize(0)
		Assertions.assertThat(result.page.total).isEqualTo(existingClients.size.toLong())
	}
}
