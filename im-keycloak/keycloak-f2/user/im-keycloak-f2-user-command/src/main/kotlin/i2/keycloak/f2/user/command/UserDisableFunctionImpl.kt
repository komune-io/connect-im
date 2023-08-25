package i2.keycloak.f2.user.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserDisableFunction
import i2.keycloak.f2.user.domain.features.command.UserDisabledEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDisableFunctionImpl {

	@Bean
	fun userDisableFunction(): UserDisableFunction = keycloakF2Function { cmd, client ->
		try {
			val userResource = client.user(cmd.id)

			val representation = userResource.toRepresentation().apply {
				isEnabled = false
			}

			userResource.update(representation)
			UserDisabledEvent(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.id}] Error disabling",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
