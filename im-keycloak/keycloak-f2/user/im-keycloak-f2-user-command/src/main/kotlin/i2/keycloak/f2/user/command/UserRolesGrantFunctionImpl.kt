package i2.keycloak.f2.user.command

import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantedEvent
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.realm.client.config.AuthRealmClient
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
			client.addUserRealmRole(cmd.realmId, cmd.id, cmd.roles)
		} else {
			client.addUserClientRoles(cmd)
		}
		UserRolesGrantedEvent(cmd.id)
	}

	fun AuthRealmClient.addUserRealmRole(realmId: RealmId, userId: UserId, roles: List<String>) {
		val roleRepresentations = roles.map { role ->
			getRoleRepresentation(realmId, role)
		}
		getUserRealmRolesResource(realmId, userId).add(roleRepresentations)
	}

	fun AuthRealmClient.getRoleRepresentation(realmId: RealmId, role: String): RoleRepresentation {
		return getRoleResource(realmId, role).toRepresentation()
	}

	private fun AuthRealmClient.getUserRealmRolesResource(realmId: RealmId, userId: String): RoleScopeResource {
		return getUserResource(realmId, userId).roles().realmLevel()
	}

	private fun AuthRealmClient.addUserClientRoles(cmd: UserRolesGrantCommand) {
		val clientKeycloakId = clients(cmd.realmId).findByClientId(cmd.clientId!!).first().id
		val roleToAdd = cmd.roles.map { role ->
			getClientResource(cmd.realmId, clientKeycloakId).roles().get(role).toRepresentation()
		}
		getUserResource(cmd.realmId, cmd.id).roles().clientLevel(clientKeycloakId).add(roleToAdd)
	}
}
