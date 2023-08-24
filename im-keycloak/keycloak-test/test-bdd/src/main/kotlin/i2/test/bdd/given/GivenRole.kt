package i2.test.bdd.given

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import i2.keycloak.f2.realm.domain.RealmId
import kotlinx.coroutines.runBlocking

class GivenRole(
	val client: KeycloakClient
) {
	fun withRole(realmId: RealmId, roleId: RoleIdentifier, composite: List<RoleIdentifier> = emptyList()): RoleIdentifier = runBlocking {
//		val cmd = RoleCreateCommand(
//			name = roleId,
//			description = "description",
//			isClientRole = false,
//			composites = composite,
//			auth = client.auth,
//			realmId = realmId
//		)
//		RoleCreateFunctionImpl().roleCreateFunction().invoke(cmd).id
        TODO()
	}
}

fun GivenKC.role() = GivenRole(client)
