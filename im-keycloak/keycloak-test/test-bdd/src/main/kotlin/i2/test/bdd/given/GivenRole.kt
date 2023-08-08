package i2.test.bdd.given

import f2.dsl.fnc.invoke
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.role.command.RoleCreateFunctionImpl
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
import i2.keycloak.realm.client.config.AuthRealmClient
import kotlinx.coroutines.runBlocking

class GivenRole(
	val client: AuthRealmClient
) {
	fun withRole(realmId: RealmId, roleId: RoleName, composite: List<RoleName> = emptyList()): RoleName = runBlocking {
		val cmd = RoleCreateCommand(
			name = roleId,
			description = "description",
			isClientRole = false,
			composites = composite,
			auth = client.auth,
			realmId = realmId
		)
		RoleCreateFunctionImpl().roleCreateFunction().invoke(cmd).id
	}
}

fun GivenKC.role() = GivenRole(client)
