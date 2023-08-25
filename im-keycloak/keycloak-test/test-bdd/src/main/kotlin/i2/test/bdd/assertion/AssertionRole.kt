package i2.test.bdd.assertion

import city.smartb.im.commons.model.RealmId
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RoleResource
import org.keycloak.representations.idm.RoleRepresentation
import javax.ws.rs.NotFoundException


fun AssertionKC.role(keycloak: Keycloak): AssertionRole = AssertionRole(keycloak)

class AssertionRole(
	private val keycloak: Keycloak,
) {
	companion object

	fun exists(realmId: RealmId, roleName: RoleIdentifier) {
		try {
			val role = getRoleRepresentation(realmId, roleName)
			Assertions.assertThat(role).isNotNull
		} catch (e: NotFoundException) {
			Assertions.fail("Role[${roleName} not found]", e)
		}
	}

	fun notExists(realmId: RealmId, roleName: RoleIdentifier) {
		try {
			getRoleRepresentation(realmId, roleName)
			Assertions.fail("Role[${roleName} exists]")
		} catch (e: NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: RealmId, roleName: RoleIdentifier): RoleAssert {
		exists(realmId, roleName)
		val roleResource = getRoleResource(realmId, roleName)
		return RoleAssert(
			role = roleResource.toRepresentation(),
			roleComposites = roleResource.roleComposites
		)
	}

	private fun getRoleRepresentation(realmId: String, roleName: RoleIdentifier): RoleRepresentation {
		return getRoleResource(realmId, roleName).toRepresentation()
	}

	private fun getRoleResource(realmId: RealmId, roleName: RoleIdentifier): RoleResource {
		return keycloak.realm(realmId).roles().get(roleName)
	}

	inner class RoleAssert(
		private val role: RoleRepresentation,
		private val roleComposites: Set<RoleRepresentation>
	) {
		fun hasFields(
            id: String = role.id,
            name: RoleIdentifier = role.name,
            description: String? = role.description,
            isClientRole: Boolean = role.clientRole,
            composites: Iterable<RoleIdentifier> = role.composites?.realm ?: emptyList(),
		) {
			Assertions.assertThat(id).isEqualTo(role.id)
			Assertions.assertThat(name).isEqualTo(role.name)
			Assertions.assertThat(description).isEqualTo(role.description)
			Assertions.assertThat(isClientRole).isEqualTo(role.clientRole)
			if (composites.count() > 0) {
				Assertions.assertThat(roleComposites).isNotNull
				Assertions.assertThat(composites).containsAll(roleComposites.map(RoleRepresentation::getName))
			} else {
				Assertions.assertThat(role.composites?.realm).isNullOrEmpty()
			}
		}
	}
}
