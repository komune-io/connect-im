package i2.test.bdd.assertion

import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName
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

	fun exists(realmId: RealmId, roleName: RoleName) {
		try {
			val role = getRoleRepresentation(realmId, roleName)
			Assertions.assertThat(role).isNotNull
		} catch (e: NotFoundException) {
			Assertions.fail("Role[${roleName} not found]", e)
		}
	}

	fun notExists(realmId: RealmId, roleName: RoleName) {
		try {
			getRoleRepresentation(realmId, roleName)
			Assertions.fail("Role[${roleName} exists]")
		} catch (e: NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: RealmId, roleName: RoleName): RoleAssert {
		exists(realmId, roleName)
		val roleResource = getRoleResource(realmId, roleName)
		return RoleAssert(
			role = roleResource.toRepresentation(),
			roleComposites = roleResource.roleComposites
		)
	}

	private fun getRoleRepresentation(realmId: String, roleName: RoleName): RoleRepresentation {
		return getRoleResource(realmId, roleName).toRepresentation()
	}

	private fun getRoleResource(realmId: RealmId, roleName: RoleName): RoleResource {
		return keycloak.realm(realmId).roles().get(roleName)
	}

	inner class RoleAssert(
		private val role: RoleRepresentation,
		private val roleComposites: Set<RoleRepresentation>
	) {
		fun hasFields(
			id: RoleId = role.id,
			name: RoleName = role.name,
			description: String? = role.description,
			isClientRole: Boolean = role.clientRole,
			composites: Iterable<RoleName> = role.composites?.realm ?: emptyList(),
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
