package i2.keycloak.f2.user.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserDeleteFunction
import i2.keycloak.f2.user.domain.features.command.UserDeletedEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDeleteFunctionImpl {

	@Bean
	fun userDeleteFunction(): UserDeleteFunction = keycloakF2Function { cmd, client ->
		try {
			client.user(cmd.id).remove()
			UserDeletedEvent(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] Error deleting User[${cmd.id}]",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
