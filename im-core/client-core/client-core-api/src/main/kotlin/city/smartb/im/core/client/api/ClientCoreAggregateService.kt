package city.smartb.im.core.client.api

import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.core.client.domain.command.ClientCreateCommand
import city.smartb.im.core.client.domain.command.ClientCreatedEvent
import city.smartb.im.core.client.domain.command.ClientGrantClientRolesCommand
import city.smartb.im.core.client.domain.command.ClientGrantRealmRolesCommand
import city.smartb.im.core.client.domain.command.ClientGrantedClientRolesEvent
import city.smartb.im.core.client.domain.command.ClientGrantedRealmRolesEvent
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.infra.keycloak.toEntityCreatedId
import org.keycloak.representations.idm.ClientRepresentation
import org.keycloak.representations.idm.ProtocolMapperRepresentation
import org.springframework.stereotype.Service

@Service
class ClientCoreAggregateService(
    private val keycloakClientProvider: KeycloakClientProvider
) {
    suspend fun create(command: ClientCreateCommand): ClientCreatedEvent {
        val keycloakClient = keycloakClientProvider.get()

        val response = ClientRepresentation().apply {
            clientId = command.identifier
            secret = command.secret
            isDirectAccessGrantsEnabled = command.isDirectAccessGrantsEnabled
            isServiceAccountsEnabled = command.isServiceAccountsEnabled
            authorizationServicesEnabled = command.authorizationServicesEnabled
            isStandardFlowEnabled = command.isStandardFlowEnabled
            isPublicClient = command.isPublicClient
            rootUrl = command.rootUrl
            redirectUris = command.redirectUris.map { url -> "${url}/*" }
            baseUrl = command.baseUrl
            adminUrl = command.adminUrl
            webOrigins = command.webOrigins
            protocolMappers = command.additionalAccessTokenClaim.map { accessTokenClaim(it) }
        }.let { keycloakClient.clients().create(it) }

        return ClientCreatedEvent(
            id = response.toEntityCreatedId(),
            identifier = command.identifier
        )
    }

    suspend fun grantClientRoles(command: ClientGrantClientRolesCommand): ClientGrantedClientRolesEvent {
        val keycloakClient = keycloakClientProvider.get()

        val newRoles = command.roles.mapAsync { role ->
            keycloakClient.client(command.providerClientId).roles().get(role).toRepresentation()
        }

        keycloakClient.user(keycloakClient.client(command.id).serviceAccountUser.id)
            .roles()
            .clientLevel(command.providerClientId)
            .add(newRoles)

        return ClientGrantedClientRolesEvent(command.id)
    }

    suspend fun grantRealmRoles(command: ClientGrantRealmRolesCommand): ClientGrantedRealmRolesEvent {
        val keycloakClient = keycloakClientProvider.get()

        val newRoles = command.roles.mapAsync { role ->
            keycloakClient.role(role).toRepresentation()
        }

        keycloakClient.user(keycloakClient.client(command.id).serviceAccountUser.id)
            .roles()
            .realmLevel()
            .add(newRoles)

        return ClientGrantedRealmRolesEvent(command.id)
    }

    private fun accessTokenClaim(name: String): ProtocolMapperRepresentation {
        return ProtocolMapperRepresentation().apply {
            this.name = name
            protocol = "openid-connect"
            protocolMapper = "oidc-usermodel-attribute-mapper"
            config = mapOf(
                "userinfo.token.claim" to "true",
                "user.attribute" to name,
                "id.token.claim" to "false",
                "access.token.claim" to "true",
                "claim.name" to name,
                "jsonType.label" to "String"
            )
        }
    }
}
