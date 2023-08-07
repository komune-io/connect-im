package i2.keycloak.f2.user.command

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsCommand
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsFunction
import i2.keycloak.f2.user.domain.features.command.UserUpdateEmailFunction
import i2.keycloak.f2.user.domain.features.command.UserUpdatedEmailEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserUpdateEmailFunctionImpl(
	private val userEmailSendActionsFunction: UserEmailSendActionsFunction
) {

	@Bean
	fun userUpdateEmailFunction(): UserUpdateEmailFunction = keycloakF2Function { cmd, client ->
		try {
			val userResource = client.getUserResource(cmd.realmId, cmd.userId)
			val userRepresentation = userResource.toRepresentation().apply {
				email = cmd.email
				isEmailVerified = false
			}
			userResource.update(userRepresentation)

			if (cmd.sendVerificationEmail) {
				UserEmailSendActionsCommand(
					userId = cmd.userId,
					clientId = cmd.clientId,
					redirectUri = cmd.redirectUri,
					actions = listOf("VERIFY_EMAIL"),
					realmId = cmd.realmId,
					auth = cmd.auth
				).invokeWith(userEmailSendActionsFunction)
			}

			UserUpdatedEmailEvent(cmd.userId)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.userId}] Error updating email",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
