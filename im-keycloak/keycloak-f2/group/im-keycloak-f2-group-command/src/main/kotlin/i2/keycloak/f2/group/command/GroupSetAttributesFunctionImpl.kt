package i2.keycloak.f2.group.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesEvent
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupSetAttributesFunctionImpl {

	@Bean
	fun groupSetAttributesFunction(): GroupSetAttributesFunction = keycloakF2Function { cmd, client ->
		try {
			val groupResource = client.group(cmd.id)

			val groupRepresentation = groupResource.toRepresentation().apply {
				cmd.attributes.forEach { (key, value) -> singleAttribute(key, value) }
			}

			groupResource.update(groupRepresentation)
			GroupSetAttributesEvent(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] Group[${cmd.id}] Error setting attributes",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
