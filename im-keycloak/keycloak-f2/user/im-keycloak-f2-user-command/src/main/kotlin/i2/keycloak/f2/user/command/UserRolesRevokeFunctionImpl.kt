package i2.keycloak.f2.user.command

import city.smartb.im.commons.model.UserId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.role.domain.defaultRealmRole
import i2.keycloak.f2.user.domain.features.command.UserRolesRevokeFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesRevokedEvent
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

		client.removeUserRole(cmd.id, rolesToRevoke)
		UserRolesRevokedEvent(cmd.id)
	}

	fun KeycloakClient.removeUserRole(userId: UserId, roles: List<String>) {
		val roleRepresentations = roles.map { role ->
			getRoleRepresentation(role)
		}
		getUserRealmRolesResource(userId).remove(roleRepresentations)
	}

	fun KeycloakClient.getRoleRepresentation(role: String): RoleRepresentation {
		return role(role).toRepresentation()
	}

	private fun KeycloakClient.getUserRealmRolesResource(userId: String): RoleScopeResource {
		return user(userId).roles().realmLevel()
	}
}
