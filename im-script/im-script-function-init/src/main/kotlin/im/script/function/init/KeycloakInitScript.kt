package im.script.function.init

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.AuthRealmClientSecret
import city.smartb.im.commons.utils.ParserUtils
import city.smartb.im.core.client.api.ClientCoreAggregateService
import city.smartb.im.core.client.api.ClientCoreFinderService
import city.smartb.im.core.client.domain.command.ClientGrantClientRolesCommand
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import city.smartb.im.f2.space.lib.SpaceAggregateService
import city.smartb.im.f2.space.lib.SpaceFinderService
import city.smartb.im.space.domain.features.command.SpaceCreateCommand
import im.script.function.core.model.AppClient
import im.script.function.core.model.AuthContext
import im.script.function.core.model.PermissionData
import im.script.function.core.service.ClientInitService
import im.script.function.core.service.ScriptFinderService
import im.script.function.init.config.AdminUserData
import im.script.function.init.config.KeycloakInitProperties
import im.script.function.init.service.InitService
import im.script.gateway.conguration.config.ImScriptInitProperties
import im.script.gateway.conguration.config.base.toAuthRealm
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class KeycloakInitScript(
    private val clientInitService: ClientInitService,
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
    private val imScriptInitProperties: ImScriptInitProperties,
    private val initService: InitService,
    private val scriptFinderService: ScriptFinderService,
    private val spaceAggregateService: SpaceAggregateService,
    private val spaceFinderService: SpaceFinderService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService
) {
    private val logger = LoggerFactory.getLogger(KeycloakInitScript::class.java)

    suspend fun run(jsonPath: String) {
        val properties = ParserUtils.getConfiguration(jsonPath, Array<KeycloakInitProperties>::class.java)
        properties.forEach { runOne(it) }
    }

    suspend fun runOne(properties: KeycloakInitProperties) {
        val masterAuth = imScriptInitProperties.auth.toAuthRealm()
        withContext(AuthContext(masterAuth)) {
            logger.info("Initializing IM client...")
            initImClient(properties)
            logger.info("Initialized IM client")

            logger.info("Initializing Realm [${properties.realmId}]...")
            initRealm(properties)
            logger.info("Initialized Realm")
        }

        val newRealmAuth = imScriptInitProperties.auth.toAuthRealm(properties.realmId)
        withContext(AuthContext(newRealmAuth)) {
            logger.info("Initializing Clients [${properties.adminClients.size}]...")
            initAdminClient(properties)
            logger.info("Initialized Clients [${properties.adminClients.size}]")

            logger.info("Initializing Generic permissions...")
            initImPermissions(properties)
            logger.info("Initialized Generic permissions")

            properties.adminUsers.forEach { adminUser ->
                logger.info("Initializing Admin user [${adminUser.email}]...")
                properties.initAdmin(newRealmAuth, adminUser)
                logger.info("Initialized Admin")
            }
            logger.info("Initialized Admin users")
        }
    }

    private suspend fun initImClient(properties: KeycloakInitProperties) {
        val imClientId = AppClient(
            clientId = properties.imMasterClient.clientId,
            clientSecret = properties.imMasterClient.clientSecret,
            roles = null,
            realmManagementRoles = emptyList()
        ).let { clientInitService.initAppClient(it) }

        val realmClientId = clientCoreFinderService.getByIdentifier("${properties.realmId}-realm").id
        val realmClientRoles = clientCoreFinderService.listClientRoles(realmClientId)
        ClientGrantClientRolesCommand(
            id = imClientId,
            providerClientId = realmClientId,
            roles = realmClientRoles
        ).let { clientCoreAggregateService.grantClientRoles(it) }
    }

    private suspend fun initAdminClient(properties: KeycloakInitProperties) = coroutineScope {
        properties.adminClients.map { appClient ->
            async {
                logger.info("Initializing Clients [${appClient.clientId}]...")
                clientInitService.initAppClient(appClient)
                logger.info("Initialized Client")
            }
        }.awaitAll()
    }

    private suspend fun initRealm(properties: KeycloakInitProperties) {
        if (spaceFinderService.getOrNull(properties.realmId) != null) {
            logger.info("Realm already created")
        } else {
            spaceAggregateService.create(SpaceCreateCommand(properties.realmId))
        }
    }

    private suspend fun KeycloakInitProperties.initAdmin(authRealm: AuthRealm, properties: AdminUserData) {
        val permissions = privilegeFinderService.listPermissions()
        properties.email.let { email ->
            if (scriptFinderService.getUser(authRealm, email, realmId) != null) {
                logger.info("User admin already created")
            } else {
                val password = properties.password ?: UUID.randomUUID().toString()
                logger.info("Creating user admin with password: $password")
                val userId = initService.createUser(
                    authRealm = authRealm,
                    username = properties.username ?: email,
                    email = email,
                    firstname = properties.firstName ?: "",
                    lastname = properties.lastName ?: "",
                    isEnable = true,
                    password = password,
                    realm = realmId
                )
                initService.grantUser(
                    authRealm = authRealm,
                    id = userId,
                    realm = realmId,
                    clientId = "realm-management",
                    "realm-admin"
                )
                initService.grantUser(
                    authRealm = authRealm,
                    id = userId,
                    realm = realmId,
                    clientId = null,
                    roles = permissions.map(PermissionDTOBase::identifier).toTypedArray()
                )
            }
        }
    }

    private suspend fun initImPermissions(properties: KeycloakInitProperties) = coroutineScope {
        val imPermissions = ParserUtils.getConfiguration("imPermissions.json", Array<PermissionData>::class.java)

        imPermissions.map { permission ->
            async {
                privilegeFinderService.getPrivilegeOrNull(permission.name)
                    ?: privilegeAggregateService.define(permission.toCommand())
            }
        }.awaitAll()
    }
}
