package i2.keycloak.f2.user.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesEvent
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserSetAttributesFunctionImpl {

	@Bean
	fun userSetAttributesFunction(): UserSetAttributesFunction = keycloakF2Function { cmd, client ->
		try {
			val userResource = client.user(cmd.id)

			val userRepresentation = userResource.toRepresentation().apply {
				cmd.attributes.forEach { (key, value) -> singleAttribute(key, value) }
			}

			userResource.update(userRepresentation)
			UserSetAttributesEvent(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.id}] Error setting attributes",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
