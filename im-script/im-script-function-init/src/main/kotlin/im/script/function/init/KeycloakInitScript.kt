package im.script.function.init

import city.smartb.im.commons.utils.ParserUtils
import im.script.function.init.config.AdminUserData
import im.script.function.init.config.KeycloakInitProperties
import im.script.function.init.service.InitService
import i2.keycloak.master.domain.AuthRealm
import im.script.function.core.service.ClientInitService
import im.script.function.core.service.ScriptFinderService
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val SUPER_ADMIN_ROLE = "super_admin"

@Service
class KeycloakInitScript(
    private val keycloakAggregateService: InitService,
    private val clientInitService: ClientInitService,
    private val scriptFinderService: ScriptFinderService,
) {
    private val logger = LoggerFactory.getLogger(KeycloakInitScript::class.java)

    fun run(authRealm: AuthRealm, jsonPath: String) = runBlocking {
        val properties = ParserUtils.getConfiguration(jsonPath, Array<KeycloakInitProperties>::class.java)
        run(authRealm, properties)
    }

    suspend fun run(authRealm: AuthRealm, properties: List<KeycloakInitProperties>) {
        properties.forEach {
            runOne(authRealm, it)
        }
    }
    suspend fun runOne(authRealm: AuthRealm, properties: KeycloakInitProperties) = runBlocking {
        logger.info("Initializing Realm [${properties.realmId}]...")
        initRealm(authRealm, properties)
        logger.info("Initialized Realm")

        logger.info("Initializing Clients [${properties.adminClient.size}]...")
        initAdminClient(authRealm, properties)

        logger.info("Initializing Base roles...")
        initBaseRoles(authRealm, properties)
        logger.info("Initialized Base roles")

        logger.info("Adding composite roles for Admin...")
        addCompositesToAdminRole(authRealm, properties)
        logger.info("Added composite roles for Admin")


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
        } ?: keycloakAggregateService.createRealm(authRealm, realmId, properties.theme, properties.smtp)
    }

    private suspend fun KeycloakInitProperties.initAdmin(authRealm: AuthRealm, properties: AdminUserData) {
        properties.email.let { email ->
            if (scriptFinderService.getUser(authRealm, email, realmId) != null) {
                logger.info("User admin already created")
            } else {
                val password = properties.password ?: UUID.randomUUID().toString()
                logger.info("Creating user admin with password: $password")
                keycloakAggregateService.createUser(
                    authRealm = authRealm,
                    username = properties.username ?: email,
                    email = email,
                    firstname = properties.firstName ?: "",
                    lastname = properties.lastName ?: "",
                    isEnable = true,
                    password = password,
                    realm = realmId
                ).let { userId ->
                    keycloakAggregateService.grantUser(
                        authRealm = authRealm,
                        id = userId,
                        realm = realmId,
                        clientId = "realm-management",
                        "realm-admin"
                    )
                    keycloakAggregateService.grantUser(
                        authRealm = authRealm,
                        id = userId,
                        realm = realmId,
                        clientId = null,
                        im.script.function.init.SUPER_ADMIN_ROLE
                    )
                }
            }
        }
    }

    private suspend fun initBaseRoles(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        val roles = properties.baseRoles
        roles.forEach { role ->
            scriptFinderService.getRole(authRealm, role, properties.realmId)
                ?: keycloakAggregateService.createRole(authRealm, role, "Role created with i2-init", emptyList(), properties.realmId)
        }
    }

    private suspend fun addCompositesToAdminRole(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        val composites = properties.baseRoles.filter { it != SUPER_ADMIN_ROLE }
        keycloakAggregateService.roleAddComposites(authRealm, SUPER_ADMIN_ROLE, composites, properties.realmId)
    }
}
