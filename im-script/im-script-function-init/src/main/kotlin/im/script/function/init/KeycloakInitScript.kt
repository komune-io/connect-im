package im.script.function.init

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.utils.ParserUtils
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
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
    private val imScriptInitProperties: ImScriptInitProperties,
    private val initService: InitService,
    private val scriptFinderService: ScriptFinderService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService
) {
    private val logger = LoggerFactory.getLogger(KeycloakInitScript::class.java)

    suspend fun run(jsonPath: String) {
        val properties = ParserUtils.getConfiguration(jsonPath, Array<KeycloakInitProperties>::class.java)
        properties.forEach {
            val auth = imScriptInitProperties.auth.toAuthRealm(it.realmId)
            withContext(AuthContext(auth)) {
                runOne(auth, it)
            }
        }
    }

    suspend fun runOne(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        logger.info("Initializing Realm [${properties.realmId}]...")
        initRealm(authRealm, properties)
        logger.info("Initialized Realm")

        logger.info("Initializing Clients [${properties.adminClient.size}]...")
        initAdminClient(authRealm, properties)

        logger.info("Initializing Generic permissions...")
        initImPermissions(properties)
        logger.info("Initialized Generic permissions")

        properties.adminUser.forEach { adminUser ->
            logger.info("Initializing Admin user [${adminUser.email}]...")
            properties.initAdmin(authRealm, adminUser)
            logger.info("Initialized Admin")
        }
        logger.info("Initialized Admin users")

    }

    private suspend fun initAdminClient(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        properties.adminClient.map { appClient ->
            logger.info("Initializing Clients [${appClient.clientId}]...")
            val realmManagementRoles = appClient.realmManagementRoles ?: listOf(
                "create-client",
                "manage-clients",
                "manage-users",
                "manage-realm",
                "view-clients",
                "view-users"
            )
            appClient.copy(realmManagementRoles = realmManagementRoles)
        }.forEach { appClient ->
            clientInitService.initAppClient(authRealm, properties.realmId, appClient)
            logger.info("Initialized Client")
        }
    }

    private suspend fun initRealm(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        val realmId = properties.realmId
        scriptFinderService.getRealm(authRealm, realmId)?.let {
          logger.info("Realm already created")
        } ?: initService.createRealm(authRealm, realmId, properties.theme, properties.smtp)
    }

    private suspend fun KeycloakInitProperties.initAdmin(authRealm: AuthRealm, properties: AdminUserData) {
        val permissions = privilegeFinderService.listPermissions()
        properties.email.let { email ->
            if (scriptFinderService.getUser(authRealm, email, realmId) != null) {
                logger.info("User admin already created")
            } else {
                val password = properties.password ?: UUID.randomUUID().toString()
                logger.info("Creating user admin with password: $password")
                initService.createUser(
                    authRealm = authRealm,
                    username = properties.username ?: email,
                    email = email,
                    firstname = properties.firstName ?: "",
                    lastname = properties.lastName ?: "",
                    isEnable = true,
                    password = password,
                    realm = realmId
                ).let { userId ->
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
    }

    private suspend fun initImPermissions(properties: KeycloakInitProperties) = coroutineScope {
        val imPermissions = ParserUtils.getConfiguration("imPermissions.json", Array<PermissionData>::class.java)

        imPermissions.map { permission ->
            async {
                privilegeFinderService.getPrivilegeOrNull(permission.name)
                    ?: privilegeAggregateService.define(permission.toCommand(properties.realmId))
            }
        }.awaitAll()
    }
}
