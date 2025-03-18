package io.komune.im.core.client.api

import io.komune.im.commons.addWildcard
import io.komune.im.commons.utils.mapAsync
import io.komune.im.core.client.domain.command.ClientCreateCommand
import io.komune.im.core.client.domain.command.ClientCreatedEvent
import io.komune.im.core.client.domain.command.ClientGrantClientRolesCommand
import io.komune.im.core.client.domain.command.ClientGrantRealmRolesCommand
import io.komune.im.core.client.domain.command.ClientGrantedClientRolesEvent
import io.komune.im.core.client.domain.command.ClientGrantedRealmRolesEvent
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import io.komune.im.infra.keycloak.handleResponseError
import org.keycloak.representations.idm.ClientRepresentation
import org.keycloak.representations.idm.ProtocolMapperRepresentation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientCoreAggregateService(
    private val keycloakClientProvider: KeycloakClientProvider
) {

    private val logger = LoggerFactory.getLogger(ClientCoreAggregateService::class.java)

    suspend fun create(command: ClientCreateCommand): ClientCreatedEvent {
        logger.info("Creating client with identifier: ${command.identifier}")
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
            redirectUris = command.redirectUris.map { url -> url.addWildcard() }
            baseUrl = command.baseUrl
            adminUrl = command.adminUrl
            webOrigins = command.webOrigins
            protocolMappers = command.additionalAccessTokenClaim.map { accessTokenClaim(it) }
        }.let { keycloakClient.clients().create(it) }

        val clientCreatedEvent = ClientCreatedEvent(
            id = response.handleResponseError("Client"),
            identifier = command.identifier
        )
        logger.info("Created client with identifier: ${command.identifier}")
        return clientCreatedEvent
    }

    suspend fun grantClientRoles(command: ClientGrantClientRolesCommand): ClientGrantedClientRolesEvent {
        logger.info("Granting client roles[${command.roles.joinToString(",")}] for client ID: ${command.id}")
        val keycloakClient = keycloakClientProvider.get()
        val newRoles = command.roles.mapAsync { role ->
            keycloakClient.client(command.providerClientId).roles().get(role).toRepresentation()
                .also {
                    logger.info("Fetched role representation for role: $role")
                }
        }
        keycloakClient.user(keycloakClient.client(command.id).serviceAccountUser.id)
            .roles()
            .clientLevel(command.providerClientId)
            .add(newRoles)

        return ClientGrantedClientRolesEvent(command.id).also {
            logger.info("Granted client roles[${newRoles.joinToString(",") { it.name }}] for client ID: ${command.id}")
        }
    }

    suspend fun grantRealmRoles(command: ClientGrantRealmRolesCommand): ClientGrantedRealmRolesEvent {
        logger.info("Granting realm roles[${command.roles.joinToString(",")}] for client ID: ${command.id}")
        val keycloakClient = keycloakClientProvider.get()

        val newRoles = command.roles.mapAsync { role ->
            keycloakClient.role(role).toRepresentation()
        }

        keycloakClient.user(keycloakClient.client(command.id).serviceAccountUser.id)
            .roles()
            .realmLevel()
            .add(newRoles)

        return ClientGrantedRealmRolesEvent(command.id).also {
            logger.info("Granted realm roles[${newRoles.joinToString(",") { it.name }}] for client ID: ${command.id}")
        }
    }

    private fun accessTokenClaim(name: String): ProtocolMapperRepresentation {
        logger.info("Creating access token claim for name: $name")
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
