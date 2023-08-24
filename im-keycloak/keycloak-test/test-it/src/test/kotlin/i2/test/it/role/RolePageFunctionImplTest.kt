//package i2.test.it.role
//
//import i2.test.bdd.given.GivenKC
//import i2.test.bdd.given.auth
//import i2.test.bdd.given.realm
//import i2.test.bdd.testcontainers.I2KeycloakTest
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.Test
//
//class RolePageFunctionImplTest: I2KeycloakTest() {
//
//	private val client = GivenKC().auth().withMasterRealmClient()
//	private val realmId = GivenKC(client).realm().withTestRealm()
//
//	@Test
//	fun `should get page of role`(): Unit = runBlocking {
//		val existingRoles = client.roles(realmId).list()
//		val newRoles = (0..6).map {
//			GivenKC(client).role().withRole(realmId, UUID.randomUUID().toString())
//		}
//
//		val cmd = RolePageQuery(
//			realmId = realmId,
//			auth = client.auth,
//			page = PagePagination(
//				page = 0,
//				size = 5
//			)
//		)
//		val result = RolePageFunctionImpl().i2RolePageFunction().invoke(cmd)
//
//		Assertions.assertThat(result.page.items).hasSize(cmd.page.size!!)
//		Assertions.assertThat(result.page.total).isEqualTo(existingRoles.size + newRoles.size.toLong())
//	}
//}
