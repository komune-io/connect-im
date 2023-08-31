package i2.keycloak.f2.user.command

import city.smartb.im.infra.keycloak.client.KeycloakClient
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantedEvent
import i2.keycloak.f2.user.domain.model.UserId
import org.keycloak.admin.client.resource.RoleScopeResource
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserRolesGrantFunctionImpl {

	@Bean
	fun userRolesGrantFunction(): UserRolesGrantFunction = keycloakF2Function { cmd, client ->
		if (cmd.roles.isEmpty()) {
			return@keycloakF2Function UserRolesGrantedEvent(cmd.id)
		}

		if (cmd.clientId == null) {
			client.addUserRealmRole(cmd.id, cmd.roles)
		} else {
			client.addUserClientRoles(cmd)
		}
		UserRolesGrantedEvent(cmd.id)
	}

	fun KeycloakClient.addUserRealmRole(userId: UserId, roles: List<String>) {
		val roleRepresentations = roles.map { role ->
			getRoleRepresentation(role)
		}
		getUserRealmRolesResource(userId).add(roleRepresentations)
	}

	fun KeycloakClient.getRoleRepresentation(role: String): RoleRepresentation {
		return role(role).toRepresentation()
	}

	private fun KeycloakClient.getUserRealmRolesResource(userId: String): RoleScopeResource {
		return user(userId).roles().realmLevel()
	}

	private fun KeycloakClient.addUserClientRoles(cmd: UserRolesGrantCommand) {
		val clientKeycloakId = clients().findByClientId(cmd.clientId!!).first().id
		val roleToAdd = cmd.roles.map { role ->
			client(clientKeycloakId).roles().get(role).toRepresentation()
		}
		user(cmd.id).roles().clientLevel(clientKeycloakId).add(roleToAdd)
	}
}
