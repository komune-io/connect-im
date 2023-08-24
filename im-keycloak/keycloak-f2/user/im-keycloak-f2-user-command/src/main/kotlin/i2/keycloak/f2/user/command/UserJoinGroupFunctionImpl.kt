package i2.keycloak.f2.user.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupFunction
import i2.keycloak.f2.user.domain.features.command.UserJoinedGroupEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserJoinGroupFunctionImpl {

	@Bean
	fun userJoinGroupFunction(): UserJoinGroupFunction = keycloakF2Function { cmd, client ->
		try {
			val groupsLeft = if (cmd.leaveOtherGroups == true) {
				client.user(cmd.id)
					.groups().map { group ->
						client.user(cmd.id).leaveGroup(group.id)
						group.id
					}
			} else emptyList()

			client.user(cmd.id).joinGroup(cmd.groupId)

			UserJoinedGroupEvent(cmd.id, cmd.groupId, groupsLeft = groupsLeft)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.id}] Error joining Group[${cmd.groupId}]",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
