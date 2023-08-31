package i2.keycloak.f2.client.command

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.toEntityCreatedId
import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.client.domain.features.command.ClientCreatedEvent
import i2.keycloak.f2.commons.app.keycloakF2Function
import org.keycloak.representations.idm.ClientRepresentation
import org.keycloak.representations.idm.ProtocolMapperRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientCreateFunctionImpl {

	@Bean
	fun clientCreateFunction(): ClientCreateFunction = keycloakF2Function { cmd, keycloakClient ->
		buildClient(cmd).let { client ->
            keycloakClient.createClient(client)
		}.let { id ->
			ClientCreatedEvent(id)
		}
	}

	private fun buildClient(cmd: ClientCreateCommand): ClientRepresentation {
		return ClientRepresentation().apply {
			this.clientId = cmd.clientIdentifier
			this.secret = cmd.secret
			this.isDirectAccessGrantsEnabled = cmd.isDirectAccessGrantsEnabled
			this.isServiceAccountsEnabled = cmd.isServiceAccountsEnabled
			this.authorizationServicesEnabled = cmd.authorizationServicesEnabled
			this.isStandardFlowEnabled = cmd.isStandardFlowEnabled
			this.isPublicClient = cmd.isPublicClient
			this.rootUrl = cmd.rootUrl
			this.redirectUris = cmd.redirectUris.map { url -> "${url}/*" }
			this.baseUrl = cmd.baseUrl
			this.adminUrl = cmd.adminUrl
			this.webOrigins = cmd.webOrigins
			this.protocolMappers = cmd.protocolMappers.map { (key, value) -> fieldMapper(key, value) }
		}
	}

	private fun fieldMapper(name: String, value: String): ProtocolMapperRepresentation {
		return ProtocolMapperRepresentation().apply {
			this.name = name
			this.protocol = "openid-connect"
			this.protocolMapper = "oidc-usermodel-attribute-mapper"
			this.config = mapOf(
				"userinfo.token.claim" to "true",
				"user.attribute" to name,
				"id.token.claim" to "false",
				"access.token.claim" to "true",
				"claim.name" to name,
				"claim.value" to value,
				"jsonType.label" to "String"
			)
		}
	}

	private fun KeycloakClient.createClient(client: ClientRepresentation): String {
		return this.clients().create(client).toEntityCreatedId()
	}
}
