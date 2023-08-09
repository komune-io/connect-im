package im.script.function.init

import city.smartb.im.commons.utils.ParserUtils
import im.script.function.init.config.AdminUserData
import im.script.function.init.config.AppClient
import im.script.function.init.config.KeycloakInitProperties
import im.script.function.init.service.InitService
import im.script.function.init.service.InitFinderService
import i2.keycloak.master.domain.AuthRealm
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val SUPER_ADMIN_ROLE = "super_admin"

@Service
class KeycloakInitScript(
    private val keycloakAggregateService: InitService,
    private val keycloakFinderService: InitFinderService,
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
        logger.info("Initializing Realm [${properties.realm}]...")
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

    private suspend fun KeycloakInitScript.initAdminClient(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        properties.adminClient.forEach { appClient ->
            logger.info("Initializing Clients [${appClient.clientId}]...")
            properties.initAdminClient(authRealm, appClient)
            logger.info("Initialized Client")
        }
    }

    private suspend fun initRealm(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        val realmId = properties.realm
        keycloakFinderService.getRealm(authRealm, realmId)?.let {
          logger.info("Realm already created")
        } ?: keycloakAggregateService.createRealm(authRealm, realmId, properties.theme, properties.smtp)
    }

    private suspend fun KeycloakInitProperties.initAdminClient(
        authRealm: AuthRealm, properties: AppClient
    ) = createClientIfNotExists(authRealm, properties) { clientId ->
        val secret = properties.clientSecret ?: UUID.randomUUID().toString()
        logger.info("Creating admin client with secret: $secret")
        keycloakAggregateService.createClient(
            authRealm = authRealm,
            identifier = clientId,
            secret = secret,
            isPublic = false,
            realm = realm
        ).let {
            keycloakAggregateService.grantClient(
                authRealm = authRealm,
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

    private suspend fun KeycloakInitProperties.createClientIfNotExists(
        authRealm: AuthRealm, properties: AppClient, createClient: suspend (id: String) -> Unit
    ) {
        properties.clientId.let {
            keycloakFinderService.getClient(authRealm, properties.clientId, realm)?.let {
                logger.info("Client already created")
            } ?: createClient(properties.clientId)
        }
    }

    private suspend fun KeycloakInitProperties.initAdmin(authRealm: AuthRealm, properties: AdminUserData) {
        properties.email.let { email ->
            if (keycloakFinderService.getUser(authRealm, email, realm) != null) {
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
                    realm = realm
                ).let { userId ->
                    keycloakAggregateService.grantUser(
                        authRealm = authRealm,
                        id = userId,
                        realm = realm,
                        clientId = "realm-management",
                        "realm-admin"
                    )
                    keycloakAggregateService.grantUser(
                        authRealm = authRealm,
                        id = userId,
                        realm = realm,
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
            keycloakFinderService.getRole(authRealm, role, properties.realm)
                ?: keycloakAggregateService.createRole(authRealm, role, "Role created with i2-init", emptyList(), properties.realm)
        }
    }

    private suspend fun addCompositesToAdminRole(authRealm: AuthRealm, properties: KeycloakInitProperties) {
        val composites = properties.baseRoles.filter { it != SUPER_ADMIN_ROLE }
        keycloakAggregateService.roleAddComposites(authRealm, SUPER_ADMIN_ROLE, composites, properties.realm)
    }
}
