package i2.init.api.auth

import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val SUPER_ADMIN_ROLE = "super_admin"

@Service
class KeycloakInitService(
    private val keycloakAggregateService: KeycloakAggregateService,
    private val keycloakFinderService: KeycloakFinderService,
) {
    private val logger = LoggerFactory.getLogger(KeycloakInitService::class.java)

    fun init(properties: KeycloakInitProperties) = runBlocking {
        logger.info("Initializing Realm [${properties.realm}]...")
        initRealm(properties)
        logger.info("Initialized Realm")

        logger.info("Initializing Client [${properties.clientId}]...")
        initAdminClient(properties)
        logger.info("Initialized Client")

        logger.info("Initializing Base roles...")
        initBaseRoles(properties)
        logger.info("Initialized Base roles")

        logger.info("Adding composite roles for Admin...")
        addCompositesToAdminRole(properties)
        logger.info("Added composite roles for Admin")

        logger.info("Initializing Admin user [${properties.username}]...")
        initAdmin(properties)
        logger.info("Initialized Admin")
    }

    private suspend fun initRealm(properties: KeycloakInitProperties) {
        val realmId = properties.realm
        keycloakFinderService.getRealm(realmId)?.let {
          logger.info("Realm already created")
        } ?: keycloakAggregateService.createRealm(realmId, properties.theme, properties.smtpConfig)
    }

    private suspend fun initAdminClient(properties: KeycloakInitProperties) = createClientIfNotExists(properties) { clientId ->
        val secret = properties.clientSecret ?: UUID.randomUUID().toString()
        logger.info("Creating admin client with secret: $secret")
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            isPublic = false,
            realm = properties.realm
        ).let {
            keycloakAggregateService.grantClient(
                id = clientId,
                realm = properties.realm,
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

    private suspend fun createClientIfNotExists(properties: KeycloakInitProperties, createClient: suspend (id: String) -> Unit) {
        properties.clientId?.let {
            keycloakFinderService.getClient(properties.clientId, properties.realm)?.let {
                logger.info("Client already created")
            } ?: createClient(properties.clientId)
        }
    }

    private suspend fun initAdmin(properties: KeycloakInitProperties) {
        properties.email?.let { email ->
            if (keycloakFinderService.getUser(email, properties.realm) != null) {
                logger.info("User admin already created")
            } else {
                val password = properties.password ?: UUID.randomUUID().toString()
                logger.info("Creating user admin with password: $password")
                keycloakAggregateService.createUser(
                    username = properties.username ?: email,
                    email = email,
                    firstname = properties.firstname ?: "",
                    lastname = properties.lastname ?: "",
                    isEnable = true,
                    password = password,
                    realm = properties.realm
                ).let { userId ->
                    keycloakAggregateService.grantUser(
                        id = userId,
                        realm = properties.realm,
                        clientId = "realm-management",
                        "realm-admin"
                    )
                    keycloakAggregateService.grantUser(
                        id = userId,
                        realm = properties.realm,
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
