package i2.test.bdd.given

import f2.dsl.fnc.invoke
import i2.keycloak.f2.group.command.GroupCreateFunctionImpl
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.group.groupCreateCommand
import kotlinx.coroutines.runBlocking

class GivenGroup(
	private val client: AuthRealmClient
) {
	fun withGroup(realmId: RealmId, groupName: String): GroupId = withGroup(
		DataTest.groupCreateCommand(
			realmId = realmId,
			auth = client.auth,
			name = "name-${groupName}",
			attributes = mapOf("zeKey" to "zeFirstValue")
		)
	)

	fun withGroup(cmd: GroupCreateCommand): UserId = runBlocking {
		GroupCreateFunctionImpl().groupCreateFunction().invoke(cmd).id
	}
}

fun GivenKC.group() = GivenGroup(client)
