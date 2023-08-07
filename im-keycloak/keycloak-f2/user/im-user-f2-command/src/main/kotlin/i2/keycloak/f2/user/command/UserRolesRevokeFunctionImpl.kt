package i2.keycloak.f2.user.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.role.domain.defaultRealmRole
import i2.keycloak.f2.user.domain.features.command.UserRolesRevokeFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesRevokedEvent
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.realmsResource
import org.keycloak.admin.client.resource.RoleScopeResource
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserRolesRevokeFunctionImpl {

	@Bean
	fun userRolesRevokeFunction(): UserRolesRevokeFunction = keycloakF2Function { cmd, client ->
		val rolesToRevoke = defaultRealmRole(cmd.realmId).let { defaultRole ->
			cmd.roles.filter { it != defaultRole }
		}

		if (rolesToRevoke.isEmpty()) {
			return@keycloakF2Function UserRolesRevokedEvent(cmd.id)
		}

		client.removeUserRole(cmd.id, rolesToRevoke, cmd.realmId)
		UserRolesRevokedEvent(cmd.id)
	}

	fun AuthRealmClient.removeUserRole(userId: UserId, roles: List<String>, realmId: RealmId) {
		val roleRepresentations = roles.map { role ->
			getRoleRepresentation(role, realmId)
		}
		getUserRealmRolesResource(userId, realmId).remove(roleRepresentations)
	}

	fun AuthRealmClient.getRoleRepresentation(role: String, realmId: RealmId): RoleRepresentation {
		return realmsResource(realmId).roles().get(role).toRepresentation()
	}

	private fun AuthRealmClient.getUserRealmRolesResource(userId: String, realmId: RealmId): RoleScopeResource {
		return getUserResource(realmId, userId).roles().realmLevel()
	}
}
