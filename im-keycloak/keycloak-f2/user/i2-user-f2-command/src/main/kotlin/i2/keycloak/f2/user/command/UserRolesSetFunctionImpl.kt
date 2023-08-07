package i2.keycloak.f2.user.command

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesRevokeCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesRevokeFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesSetEvent
import i2.keycloak.f2.user.domain.features.command.UserRolesSetFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserRolesSetFunctionImpl(
	private val userRolesGrantFunction: UserRolesGrantFunction,
	private val userRolesRevokeFunction: UserRolesRevokeFunction
) {

	@Bean
	fun userRolesSetFunction(): UserRolesSetFunction = keycloakF2Function { cmd, client ->
		val oldRoles = client.getUserResource(cmd.realmId, cmd.id).roles().realmLevel().listAll()

		UserRolesRevokeCommand(
			id = cmd.id,
			roles = oldRoles.map { it.name },
			auth = cmd.auth,
			realmId = cmd.realmId
		).invokeWith(userRolesRevokeFunction)

		UserRolesGrantCommand(
			id = cmd.id,
			roles = cmd.roles,
			auth = cmd.auth,
			realmId = cmd.realmId
		).invokeWith(userRolesGrantFunction)

		UserRolesSetEvent(cmd.id)
	}
}
