package io.komune.im.script.core.service

import io.komune.im.commons.addWildcard
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
    private val logger = LoggerFactory.getLogger(ClientInitService::class.java)

    suspend fun getAppClientId(clientIdentifier: ClientIdentifier): ClientModel? =
        clientCoreFinderService.getByIdentifierOrNull(clientIdentifier)

    suspend fun initAppClient(appClient: AppClient): ClientId = init(
        logPrefix = "AppClient[clientId: ${appClient.clientId}]",
        logger = logger,
        find = {
            clientCoreFinderService.getByIdentifierOrNull(appClient.clientId)?.id
        },
        create = {
            val secret = appClient.clientSecret ?: UUID.randomUUID().toString()
            val clientId = appClient.createClient(secret)

            appClient.grantRealmRoles(clientId)
            appClient.grantClientRoles(clientId)

            val secretLogging = if(logger.isTraceEnabled) secret else "******"
            logger.info("Client [${appClient.clientId}] secret: $secretLogging")
            clientId
        }
    )

    private suspend fun AppClient.createClient(
        secret: String
    ): ClientId = clientCoreAggregateService.create(
        ClientCreateCommand(
            identifier = this.clientId,
            secret = secret,
            isPublicClient = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            authorizationServicesEnabled = false,
            isStandardFlowEnabled = false,
            baseUrl = this.homeUrl,
            redirectUris = this.buildRedirectUrl(),
        )
    ).id

    private suspend fun AppClient.grantClientRoles(
        clientId: ClientId
    ) {
        if (this.realmManagementRoles == null || this.realmManagementRoles.isNotEmpty()) {
            val realmManagementId = clientCoreFinderService.getByIdentifier("realm-management").id
            val _ = clientCoreAggregateService.grantClientRoles(
                ClientGrantClientRolesCommand(
                    id = clientId,
                    providerClientId = realmManagementId,
                    roles = this.realmManagementRoles ?: DEFAULT_REALM_MANAGEMENT_ROLES
                )
            )
        }
    }

    private suspend fun AppClient.grantRealmRoles(
        clientId: ClientId
    ) {
        roles?.let { roles ->
            clientCoreAggregateService.grantRealmRoles(
                ClientGrantRealmRolesCommand(
                    id = clientId,
                    roles = roles
                )
            )
        }
    }

    private fun AppClient.buildRedirectUrl(): List<String> = listOfNotNull(this.homeUrl?.addWildcard())

    suspend fun initWebClient(webClient: WebClient): ClientId = init(
        logPrefix = "WebClient[clientId: ${webClient.clientId}]",
        logger = logger,
        find = {
            clientCoreFinderService.getByIdentifierOrNull(webClient.clientId)?.id
        },
        create = {
            val urls = webClient.webUrl.split(",").map { it.trim() }
            val baseUrl = urls.firstOrNull()
            clientCoreAggregateService.create(
                ClientCreateCommand(
                    identifier = webClient.clientId,
                    secret = webClient.secret,
                    isPublicClient = webClient.secret == null,
                    isDirectAccessGrantsEnabled = true,
                    isServiceAccountsEnabled = false,
                    authorizationServicesEnabled = false,
                    isStandardFlowEnabled = true,
                    rootUrl = baseUrl,
                    redirectUris = urls.map { it.addWildcard() },
                    baseUrl = baseUrl,
                    adminUrl = baseUrl,
                    webOrigins = urls,
                    additionalAccessTokenClaim = listOf(ORGANIZATION_ID_CLAIM_NAME)
                )
            ).id
        }
    )

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
}
