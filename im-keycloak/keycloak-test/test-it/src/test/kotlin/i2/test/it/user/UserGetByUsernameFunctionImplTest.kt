//package i2.test.it.user
//
//import f2.dsl.fnc.invoke
//import i2.keycloak.f2.user.domain.features.query.UserGetByUsernameFunction
//import i2.keycloak.f2.user.domain.features.query.UserGetByUsernameQuery
//import i2.test.bdd.data.DataTest
//import i2.test.bdd.data.user.userCreateCommand
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
//class UserGetByUsernameFunctionImplTest: I2KeycloakTest() {
//
//	private val client = GivenKC().auth().withMasterRealmClient()
//	private val realmId = GivenKC(client).realm().withTestRealm()
//
//	@Autowired
//	private lateinit var userGetByUsernameFunction: UserGetByUsernameFunction
//
//	@Test
//	fun `should get user when user with username exists`(): Unit = runBlocking {
//		val createCommand = DataTest.userCreateCommand(
//			realmId = realmId,
//			auth = client.auth,
//			username = UUID.randomUUID().toString()
//		)
//		GivenKC(client).user().withUser(createCommand)
//
//		val cmd = UserGetByUsernameQuery(
//			username = createCommand.username,
//			realmId = realmId,
//			auth = client.auth
//		)
//		val result = userGetByUsernameFunction.invoke(cmd)
//
//		Assertions.assertThat(result.item).isNotNull
//	}
//
//	@Test
//	fun `should not get user when no user with username exists`(): Unit = runBlocking {
//		val createCommand = DataTest.userCreateCommand(
//			realmId = realmId,
//			auth = client.auth,
//			username = UUID.randomUUID().toString()
//		)
//		GivenKC(client).user().withUser(createCommand)
//
//		val cmd = UserGetByUsernameQuery(
//			username = "invalid-username",
//			realmId = realmId,
//			auth = client.auth
//		)
//		val result = userGetByUsernameFunction.invoke(cmd)
//
//		Assertions.assertThat(result.item).isNull()
//	}
//}
