package i2.keycloak.f2.user.command

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserDisableFunction
import i2.keycloak.f2.user.domain.features.command.UserDisabledEvent
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDisableFunctionImpl {

	@Bean
	fun userDisableFunction(): UserDisableFunction = f2Function { cmd ->
		try {
			val realmClient = AuthRealmClientBuilder().build(cmd.auth)
			val userResource = realmClient.getUserResource(cmd.realmId, cmd.id)

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
