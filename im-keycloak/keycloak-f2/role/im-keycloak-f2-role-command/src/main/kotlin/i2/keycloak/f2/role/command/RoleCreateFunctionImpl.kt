package i2.keycloak.f2.role.command

import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.f2.commons.app.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
import i2.keycloak.f2.role.domain.features.command.RoleCreateFunction
import i2.keycloak.f2.role.domain.features.command.RoleCreatedEvent
import i2.keycloak.realm.client.config.AuthRealmClient
import org.apache.http.HttpStatus
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.ClientErrorException

@Configuration
class RoleCreateFunctionImpl {

	@Bean
	fun roleCreateFunction(): RoleCreateFunction = keycloakF2Function { cmd, client ->
		client.initRole(cmd)
		RoleCreatedEvent(cmd.name)
	}

	private fun AuthRealmClient.initRole(cmd: RoleCreateCommand) {
		val compositeRoles = cmd.composites.map { role ->
			getOrCreateRole(cmd, role)
		}
		createRole(cmd.realmId, cmd.name, cmd.description, cmd.isClientRole)
		getRoleResource(cmd.realmId, cmd.name).addComposites(compositeRoles)
	}


	fun AuthRealmClient.getOrCreateRole(cmd: RoleCreateCommand, roleName: RoleName): RoleRepresentation? {
		return try {
			getRoleResource(cmd.realmId, roleName).toRepresentation()
		} catch (e: ClientErrorException) {
			if(e.response.status == HttpStatus.SC_NOT_FOUND) {
				createRole(cmd.realmId, roleName, null, cmd.isClientRole)
			}
			throw  e.asI2Exception("Error trying to fetch $roleName")
		} catch (e: Exception) {
			throw e.asI2Exception("Error trying to fetch $roleName")
		}

	}

	private fun AuthRealmClient.createRole(realmId: RealmId, role: RoleName, description: String?, isClientRole: Boolean): RoleRepresentation {
		return RoleRepresentation().apply {
			this.name = role
			this.description = description
			this.clientRole = isClientRole
		}.also { roleRepresentation ->
			roles(realmId).create(roleRepresentation)
		}
	}
}
