package io.komune.im.script.init

import io.komune.im.commons.auth.AuthContext
import io.komune.im.commons.utils.ParserUtils
import io.komune.im.core.client.api.ClientCoreAggregateService
import io.komune.im.core.client.api.ClientCoreFinderService
import io.komune.im.core.client.domain.command.ClientGrantClientRolesCommand
import io.komune.im.script.core.config.properties.ImScriptInitProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.model.AppClient
import io.komune.im.script.core.model.ClientCredentials
import io.komune.im.script.core.model.DEFAULT_ROOT_CLIENT_ID
import io.komune.im.script.core.service.ClientInitService
import io.komune.im.script.init.config.ImInitProperties
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ImInitScript(
    private val clientInitService: ClientInitService,
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
    private val imScriptInitProperties: ImScriptInitProperties,
) {
    private val logger = LoggerFactory.getLogger(ImInitScript::class.java)

    suspend fun run() {
        val jsonPaths = imScriptInitProperties.json ?: return
        jsonPaths.split(";").forEach { jsonPath ->
            logger.info("Start processing configuration file [$jsonPath]...")
            val properties = ParserUtils.getConfiguration(jsonPath, ImInitProperties::class.java)

            val masterAuth = imScriptInitProperties.auth.toAuthRealm()
            withContext(AuthContext(masterAuth)) {
                logger.info("Initializing imMasterClient...")
                logger.warn("******************************")
                logger.warn("Deprecated: imMasterClient is deprecated and will be removed in the future")
                logger.warn("Deprecated: use rootClient properties instead")
                logger.warn("******************************")
                properties.imMasterClient?.initImClient()
                logger.info("Initialized imMasterClient")
                logger.info("Initializing rootClient")
                properties.rootClient?.initImClient()
                logger.info("Initialized rootClient")
            }
        }

    }

    private suspend fun ClientCredentials.initImClient() {
        val clientId = clientId ?: DEFAULT_ROOT_CLIENT_ID
        val imClientId = AppClient(
            clientId = clientId,
            clientSecret = clientSecret,
            roles = listOf("admin"),
            realmManagementRoles = emptyList()
        ).let { clientInitService.initAppClient(it) }

        val realmClientId = clientCoreFinderService.getByIdentifier("master-realm").id
        val realmClientRoles = clientCoreFinderService.listClientRoles(realmClientId)
        ClientGrantClientRolesCommand(
            id = imClientId,
            providerClientId = realmClientId,
            roles = realmClientRoles
        ).let { clientCoreAggregateService.grantClientRoles(it) }
    }
}
