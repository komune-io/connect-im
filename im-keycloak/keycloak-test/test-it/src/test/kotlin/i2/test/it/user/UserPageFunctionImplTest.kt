//package i2.test.it.user
//
//import f2.dsl.cqrs.page.PagePagination
//import f2.dsl.fnc.invoke
//import i2.keycloak.f2.user.domain.features.query.UserPageFunction
//import i2.keycloak.f2.user.domain.features.query.UserPageQuery
//import i2.test.bdd.given.GivenKC
//import i2.test.bdd.given.auth
//import i2.test.bdd.given.realm
//import i2.test.bdd.given.user
//import i2.test.bdd.testcontainers.I2KeycloakTest
//import kotlinx.coroutines.runBlocking
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import java.util.UUID
//
//class UserPageFunctionImplTest: I2KeycloakTest() {
//
//	val client = GivenKC().auth().withMasterRealmClient()
//	val realmId = GivenKC(client).realm().withRealmId(UUID.randomUUID().toString())
//
//	@Autowired
//	private lateinit var userPageFunction: UserPageFunction
//
//	@Test
//	fun `should get page of user`(): Unit = runBlocking {
//		(0..6).forEach { _ ->
//			GivenKC(client).user().withUser(realmId, UUID.randomUUID().toString())
//		}
//
//		val cmd = UserPageQuery(
//			realmId = realmId,
//			auth = client.auth,
//			page = PagePagination(
//				page = 0,
//				size = 5
//			),
//			withDisabled = false
//		)
//		val result = userPageFunction.invoke(cmd).items
//
//		Assertions.assertThat(result.items).hasSize(5)
//		Assertions.assertThat(result.total).isEqualTo(7)
//	}
//
//}
