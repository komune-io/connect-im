package i2.keycloak.f2.group.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.group.domain.features.command.GroupDeleteFunction
import i2.keycloak.f2.group.domain.features.command.GroupDeletedEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupDeleteFunctionImpl {

	@Bean
	fun groupDeleteFunction(): GroupDeleteFunction = keycloakF2Function { cmd, client ->
		try {
			client.getGroupResource(cmd.realmId, cmd.id).remove()
			GroupDeletedEvent(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] Error deleting Group[${cmd.id}]",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}

}
