package i2.test.bdd.given

import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import f2.dsl.fnc.invoke
import i2.keycloak.f2.user.command.UserCreateFunctionImpl
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.keycloak.f2.user.domain.model.UserId
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.user.userCreateCommand
import kotlinx.coroutines.runBlocking

class GivenUser(
	private val client: KeycloakClient
) {
	fun withUser(realmId: RealmId, username: String): UserId = withUser(
		DataTest.userCreateCommand(
			realmId = realmId,
			auth = client.auth,
			username = username
		)
	)

	fun withUser(cmd: UserCreateCommand): UserId = runBlocking {
		UserCreateFunctionImpl().userCreateFunction().invoke(cmd).id
	}
}

fun GivenKC.user() = GivenUser(client)
