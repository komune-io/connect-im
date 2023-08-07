package i2.keycloak.f2.user.command

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.keycloak.f2.user.domain.features.command.UserCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserCreatedCommand
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.utils.isFailure
import i2.keycloak.utils.onCreationFailure
import i2.keycloak.utils.toEntityCreatedId
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserCreateFunctionImpl {

	@Bean
	fun userCreateFunction(): UserCreateFunction = f2Function { cmd ->
		val serviceClient = AuthRealmClientBuilder().build(cmd.auth)
		val userId = createUser(serviceClient, cmd)
		UserCreatedCommand(userId)
	}

	private fun createUser(client: AuthRealmClient, cmd: UserCreateCommand): String {
		val userRepresentation = initUserRepresentation(cmd)
		return createUser(client, cmd.realmId, userRepresentation).also {
			// onboarding(client, cmd)
		}
	}

	private fun createUser(
		client: AuthRealmClient,
		realmId: String,
		user: UserRepresentation,
	): String {
		val response = client.keycloak.realm(realmId).users().create(user)
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
