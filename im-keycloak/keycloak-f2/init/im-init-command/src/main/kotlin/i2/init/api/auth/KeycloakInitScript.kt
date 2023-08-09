package i2.init.api.auth

import city.smartb.im.commons.utils.ParserUtils
import i2.init.api.auth.config.AdminUserData
import i2.init.api.auth.config.AppClient
import i2.init.api.auth.config.KeycloakInitParser
import i2.init.api.auth.config.KeycloakInitProperties
import i2.init.api.auth.service.KeycloakAggregateService
import i2.init.api.auth.service.KeycloakFinderService
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val SUPER_ADMIN_ROLE = "super_admin"

@Service
class KeycloakInitScript(
    private val keycloakAggregateService: KeycloakAggregateService,
    private val keycloakFinderService: KeycloakFinderService,
) {
    private val logger = LoggerFactory.getLogger(KeycloakInitScript::class.java)

    fun run(configPath: String) = runBlocking {
        val config = ParserUtils.getConfiguration(configPath, Array<KeycloakInitProperties>::class.java)
        run(config)
    }

    suspend fun run(properties: List<KeycloakInitProperties>) {
        properties.forEach {
            runOne(it)
        }
    }
    suspend fun runOne(properties: KeycloakInitProperties) = runBlocking {
        logger.info("Initializing Realm [${properties.realm}]...")
        initRealm(properties)
        logger.info("Initialized Realm")


        logger.info("Initializing Clients [${properties.adminClient.size}]...")
        initAdminClient(properties)

        logger.info("Initializing Base roles...")
        initBaseRoles(properties)
        logger.info("Initialized Base roles")

        logger.info("Adding composite roles for Admin...")
        addCompositesToAdminRole(properties)
        logger.info("Added composite roles for Admin")


        properties.adminUser.forEach { adminUser ->
            logger.info("Initializing Admin user [${adminUser.email}]...")
            properties.initAdmin(adminUser)
            logger.info("Initialized Admin")
        }
        logger.info("Initialized Admin users")

    }

    private suspend fun KeycloakInitScript.initAdminClient(properties: KeycloakInitProperties) {
        properties.adminClient.forEach { appClient ->
            logger.info("Initializing Clients [${appClient.clientId}]...")
            properties.initAdminClient(appClient)
            logger.info("Initialized Client")
        }
    }

    private suspend fun initRealm(properties: KeycloakInitProperties) {
        val realmId = properties.realm
        keycloakFinderService.getRealm(realmId)?.let {
          logger.info("Realm already created")
        } ?: keycloakAggregateService.createRealm(realmId, properties.theme, properties.smtp)
    }

    private suspend fun KeycloakInitProperties.initAdminClient(properties: AppClient) = createClientIfNotExists(properties) { clientId ->
        val secret = properties.clientSecret ?: UUID.randomUUID().toString()
        logger.info("Creating admin client with secret: $secret")
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            isPublic = false,
            realm = realm
        ).let {
            keycloakAggregateService.grantClient(
                id = clientId,
                realm = realm,
                roles = listOf(
                    "create-client",
                    "manage-clients",
                    "manage-users",
                    "manage-realm",
                    "view-clients",
                    "view-users"
                )
            )
        }
    }

    private suspend fun KeycloakInitProperties.createClientIfNotExists(properties: AppClient, createClient: suspend (id: String) -> Unit) {
        properties.clientId.let {
            keycloakFinderService.getClient(properties.clientId, realm)?.let {
                logger.info("Client already created")
            } ?: createClient(properties.clientId)
        }
    }

    private suspend fun KeycloakInitProperties.initAdmin(properties: AdminUserData) {
        properties.email.let { email ->
            if (keycloakFinderService.getUser(email, realm) != null) {
                logger.info("User admin already created")
            } else {
                val password = properties.password ?: UUID.randomUUID().toString()
                logger.info("Creating user admin with password: $password")
                keycloakAggregateService.createUser(
                    username = properties.username ?: email,
                    email = email,
                    firstname = properties.firstName ?: "",
                    lastname = properties.lastName ?: "",
                    isEnable = true,
                    password = password,
                    realm = realm
                ).let { userId ->
                    keycloakAggregateService.grantUser(
                        id = userId,
                        realm = realm,
                        clientId = "realm-management",
                        "realm-admin"
                    )
                    keycloakAggregateService.grantUser(
                        id = userId,
                        realm = realm,
                        clientId = null,
                        SUPER_ADMIN_ROLE
                    )
                }
            }
        }
    }

    private suspend fun initBaseRoles(properties: KeycloakInitProperties) {
        val roles = properties.baseRoles
        roles.forEach { role ->
            keycloakFinderService.getRole(role, properties.realm)
                ?: keycloakAggregateService.createRole(role, "Role created with i2-init", emptyList(), properties.realm)
        }
    }

    private suspend fun addCompositesToAdminRole(properties: KeycloakInitProperties) {
        val composites = properties.baseRoles.filter { it != SUPER_ADMIN_ROLE }
        keycloakAggregateService.roleAddComposites(SUPER_ADMIN_ROLE, composites, properties.realm)
    }
}
