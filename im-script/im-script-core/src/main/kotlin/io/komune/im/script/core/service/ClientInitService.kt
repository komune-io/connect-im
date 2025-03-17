package io.komune.im.script.core.service

import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.core.client.api.ClientCoreAggregateService
import io.komune.im.core.client.api.ClientCoreFinderService
import io.komune.im.core.client.domain.command.ClientCreateCommand
import io.komune.im.core.client.domain.command.ClientGrantClientRolesCommand
import io.komune.im.core.client.domain.command.ClientGrantRealmRolesCommand
import io.komune.im.core.client.domain.model.ClientModel
import io.komune.im.script.core.init
import io.komune.im.script.core.model.AppClient
import io.komune.im.script.core.model.WebClient
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val ORGANIZATION_ID_CLAIM_NAME = "memberOf"

@Service
class ClientInitService(
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
) {
    companion object {
        val DEFAULT_REALM_MANAGEMENT_ROLES = listOf(
            "create-client",
            "manage-clients",
            "manage-users",
            "manage-realm",
            "view-clients",
            "view-users"
        )
    }

    private val logger = LoggerFactory.getLogger(ClientInitService::class.java)

    suspend fun getAppClientId(clientIdentifier: ClientIdentifier): ClientModel? {
        return clientCoreFinderService.getByIdentifierOrNull(clientIdentifier)
    }
    suspend fun initAppClient(appClient: AppClient): ClientId {

        return init("AppClient[clientId: ${appClient.clientId}]", logger, {
            clientCoreFinderService.getByIdentifierOrNull(appClient.clientId)?.id
        }, {
            val secret = appClient.clientSecret ?: UUID.randomUUID().toString()
            val clientId = appClient.createClient(secret)

            appClient.grantRealmRoles(clientId)
            appClient.grantClientRoles(clientId)

            val secretLogging = if(logger.isTraceEnabled) secret else "******"
            logger.info("Client [${appClient.clientId}] secret: $secretLogging")
            clientId
        })


    }

    private suspend fun AppClient.createClient(
        secret: String
    ): ClientId {
        val redirectUrls = this.buildRedirectUrl()
        val clientId = ClientCreateCommand(
            identifier = this.clientId,
            secret = secret,
            isPublicClient = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            authorizationServicesEnabled = false,
            isStandardFlowEnabled = false,
            baseUrl = this.homeUrl,
            redirectUris = redirectUrls,
        ).let { clientCoreAggregateService.create(it).id }
        return clientId
    }

    private suspend fun AppClient.grantClientRoles(
        clientId: ClientId
    ) {
        if (this.realmManagementRoles == null || this.realmManagementRoles.isNotEmpty()) {
            val realmManagementId = clientCoreFinderService.getByIdentifier("realm-management").id
            ClientGrantClientRolesCommand(
                id = clientId,
                providerClientId = realmManagementId,
                roles = this.realmManagementRoles ?: DEFAULT_REALM_MANAGEMENT_ROLES
            ).let { clientCoreAggregateService.grantClientRoles(it) }
        }
    }

    private suspend fun AppClient.grantRealmRoles(
        clientId: ClientId
    ) {
        roles?.let { roles ->
            ClientGrantRealmRolesCommand(
                id = clientId,
                roles = roles
            ).let { clientCoreAggregateService.grantRealmRoles(it) }
        }
    }

    private fun AppClient.buildRedirectUrl(): List<String> {
        val redirectUrls = this.homeUrl?.let { homeUrl ->
            if (homeUrl.endsWith("/")) "$homeUrl*" else "$homeUrl/*"
        }?.let { redirectUrl -> listOf(redirectUrl) } ?: emptyList()
        return redirectUrls
    }

    suspend fun initWebClient(webClient: WebClient): ClientId {
        return init("WebClient[clientId: ${webClient.clientId}]", logger, {
            clientCoreFinderService.getByIdentifierOrNull(webClient.clientId)?.id
        }, {
            ClientCreateCommand(
                identifier = webClient.clientId,
                isPublicClient = true,
                isDirectAccessGrantsEnabled = true,
                isServiceAccountsEnabled = false,
                authorizationServicesEnabled = false,
                isStandardFlowEnabled = true,
                rootUrl = webClient.webUrl,
                redirectUris = listOf(webClient.webUrl),
                baseUrl = webClient.webUrl,
                adminUrl = webClient.webUrl,
                webOrigins = listOf(webClient.webUrl),
                additionalAccessTokenClaim = listOf(ORGANIZATION_ID_CLAIM_NAME)
            ).let { clientCoreAggregateService.create(it).id }
        })
    }
}
