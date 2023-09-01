package city.smartb.im.script.init

import city.smartb.im.commons.auth.AuthContext
import city.smartb.im.commons.utils.ParserUtils
import city.smartb.im.core.client.api.ClientCoreAggregateService
import city.smartb.im.core.client.api.ClientCoreFinderService
import city.smartb.im.core.client.domain.command.ClientGrantClientRolesCommand
import city.smartb.im.script.core.config.properties.ImScriptInitProperties
import city.smartb.im.script.core.config.properties.toAuthRealm
import city.smartb.im.script.core.model.AppClient
import city.smartb.im.script.core.service.ClientInitService
import city.smartb.im.script.init.config.ImInitProperties
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class ImInitScript(
    private val clientInitService: ClientInitService,
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
    private val imScriptInitProperties: ImScriptInitProperties,
) {
    private val logger by Logger()

    suspend fun run() {
        val jsonPath = imScriptInitProperties.json ?: return
        val properties = ParserUtils.getConfiguration(jsonPath, ImInitProperties::class.java)

        val masterAuth = imScriptInitProperties.auth.toAuthRealm()
        withContext(AuthContext(masterAuth)) {
            logger.info("Initializing IM client...")
            initImClient(properties)
            logger.info("Initialized IM client")
        }
    }

    private suspend fun initImClient(properties: ImInitProperties) {
        val imClientId = AppClient(
            clientId = properties.imMasterClient.clientId,
            clientSecret = properties.imMasterClient.clientSecret,
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
