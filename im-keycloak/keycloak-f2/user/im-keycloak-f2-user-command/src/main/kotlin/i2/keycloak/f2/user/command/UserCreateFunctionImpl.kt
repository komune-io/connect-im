package i2.keycloak.f2.user.command

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.isFailure
import city.smartb.im.infra.keycloak.onCreationFailure
import city.smartb.im.infra.keycloak.toEntityCreatedId
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.keycloak.f2.user.domain.features.command.UserCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserCreatedCommand
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserCreateFunctionImpl {

	@Bean
	fun userCreateFunction(): UserCreateFunction = keycloakF2Function { cmd, client ->
		val userId = createUser(client, cmd)
		UserCreatedCommand(userId)
	}

	private fun createUser(client: KeycloakClient, cmd: UserCreateCommand): String {
		val userRepresentation = initUserRepresentation(cmd)
		return createUser(client, userRepresentation)
	}

	private fun createUser(
		client: KeycloakClient,
		user: UserRepresentation,
	): String {
		val response = client.users().create(user)
		if (response.isFailure()) {
			response.onCreationFailure("user")
		}
		return response.toEntityCreatedId()
	}

	private fun initUserRepresentation(user: UserCreateCommand): UserRepresentation {
		val userRep = UserRepresentation()
		userRep.username = user.username
		userRep.email = user.email
		userRep.firstName = user.firstname
		userRep.lastName = user.lastname
		userRep.isEnabled = user.isEnable
		userRep.isEmailVerified = user.isEmailVerified
		user.attributes.forEach {
			userRep.singleAttribute(it.key, it.value)
		}
		userRep.singleAttribute("realmId", user.realmId)
		user.password?.let { password ->
			userRep.credentials = listOf(password.toCredentialRepresentation(CredentialRepresentation.PASSWORD, user.isPasswordTemporary))
		}
		return userRep
	}

	private fun String.toCredentialRepresentation(credentialType: String, isTemporary: Boolean): CredentialRepresentation {
		val credential = CredentialRepresentation()
		credential.type = credentialType
		credential.value = this
		credential.isTemporary = isTemporary
		return credential
	}

}
