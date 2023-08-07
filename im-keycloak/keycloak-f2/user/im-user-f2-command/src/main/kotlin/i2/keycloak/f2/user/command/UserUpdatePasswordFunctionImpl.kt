package i2.keycloak.f2.user.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserUpdatePasswordFunction
import i2.keycloak.f2.user.domain.features.command.UserUpdatedPasswordEvent
import org.keycloak.representations.idm.CredentialRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserUpdatePasswordFunctionImpl {

	@Bean
	fun userUpdatePasswordFunction(): UserUpdatePasswordFunction = keycloakF2Function { cmd, client ->
		try {
			val userResource = client.getUserResource(cmd.realmId, cmd.userId)

			CredentialRepresentation().apply {
				type = CredentialRepresentation.PASSWORD
				isTemporary = false
				value = cmd.password
			}.let(userResource::resetPassword)

			UserUpdatedPasswordEvent(cmd.userId)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.userId}] Error updating password",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
