package i2.config.api.auth

import city.smartb.im.commons.exception.NotFoundException
import i2.config.api.auth.config.AppClient
import i2.config.api.auth.config.KeycloakConfigParser
import i2.config.api.auth.config.KeycloakConfigProperties
import i2.config.api.auth.config.KeycloakUserConfig
import i2.config.api.auth.config.WebClient
import i2.config.api.auth.service.KeycloakAggregateService
import i2.config.api.auth.service.KeycloakFinderService
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val SUPER_ADMIN_ROLE = "super_admin"
const val ORGANIZATION_ID_CLAIM_NAME = "memberOf"

@Service
class KeycloakConfigService (
    private val authRealm: AuthRealm,
    private val keycloakAggregateService: KeycloakAggregateService,
    private val keycloakFinderService: KeycloakFinderService
) {
    private val logger = LoggerFactory.getLogger(KeycloakConfigService::class.java)

    fun run(configPath: String) {
        val config = KeycloakConfigParser().getConfiguration(configPath)
        run(config)
    }

    fun run(config: KeycloakConfigProperties) = runBlocking {
        logger.info("Verify Realm[${authRealm.realmId}] exists...")
        verifyRealm(authRealm.realmId)
        logger.info("Initializing Roles...")
        initRoles(config.roles, config.roleComposites)
        logger.info("Initialized Roles")

        logger.info("Initializing Clients...")
        config.webClients.forEach { initWebClient(it) }
        config.appClients.forEach { initAppClient(it) }
        logger.info("Initialized Client")

        logger.info("Initializing Users...")
        initUsers(config.users)
        logger.info("Initialized Users")
    }

    private suspend fun checkIfExists(clientId: ClientId): Boolean {
        return if (keycloakFinderService.getClient(clientId) != null) {
            logger.info("Client [$clientId] already exists.")
            true
        } else {
            false
        }
    }

    private suspend fun initAppClient(appClient: AppClient) {
        if (!checkIfExists(appClient.clientId)) {
            val secret = appClient.clientSecret ?: UUID.randomUUID().toString()
            keycloakAggregateService.createClient(
                identifier = appClient.clientId,
                secret = secret,
                isPublic = false,
                isServiceAccountsEnabled = true,
                isDirectAccessGrantsEnabled = false,
                isStandardFlowEnabled = false
            ).let { clientId ->
                appClient.roles?.toList()?.let { list ->
                    keycloakAggregateService.grantClient(
                        id = clientId,
                        roles = list
                    )
                }
                appClient.realmManagementRoles?.toList()?.let { list ->
                    keycloakAggregateService.grantRealmManagementClient(
                        id = appClient.clientId,
                        roles = list,
                    )
                }
            }
            logger.info("App secret: $secret")
        }
    }

    private suspend fun initWebClient(webClient: WebClient) {
        if (!checkIfExists(webClient.clientId)) {
            keycloakAggregateService.createClient(
                identifier = webClient.clientId,
                baseUrl = webClient.webUrl,
                isStandardFlowEnabled = true,
                isDirectAccessGrantsEnabled = false,
                isServiceAccountsEnabled = false,
                isPublic = true,
                protocolMappers = mapOf(ORGANIZATION_ID_CLAIM_NAME to ORGANIZATION_ID_CLAIM_NAME),
            )
        }
    }

    private suspend fun initRoles(roles: List<RoleName>?, roleComposites: Map<RoleName, List<RoleName>>?) {
        roles?.let {
            roles.forEach { role ->
                initRoleWithComposites(role)
            }
            addCompositesToAdmin(roles.filter { it != SUPER_ADMIN_ROLE })
        }

        roleComposites?.let {
            roleComposites.forEach { roleComposite ->
                initRoleWithComposites(roleComposite.key, roleComposite.value)
            }
        }
    }

    private suspend fun verifyRealm(realmId: RealmId) {
        keycloakFinderService.getRealm(realmId) ?: throw NotFoundException("Realm", "")
    }
    private suspend fun initRoleWithComposites(role: RoleName, composites: List<RoleName> = emptyList()) {
        keycloakFinderService.getRole(role)
            ?: keycloakAggregateService.createRole(role)

        if (composites.isNotEmpty()) {
            keycloakAggregateService.addRoleComposites(role, composites)
        }
    }

    private suspend fun addCompositesToAdmin(composites: List<RoleName>) {
        keycloakAggregateService.addRoleComposites(SUPER_ADMIN_ROLE, composites)
    }

    private suspend fun initUsers(users: List<KeycloakUserConfig>?) {
        users?.let {
            users.forEach { initUser(it) }
        }
    }

    private suspend fun initUser(user: KeycloakUserConfig) {
        keycloakFinderService.getUser(user.email)
            ?: keycloakAggregateService.createUser(
                username = user.username,
                email = user.email,
                firstname = user.firstname,
                lastname = user.lastname,
                isEnable = true,
                password = user.password
            ).let { userId ->
                keycloakAggregateService.grantUser(userId, user.role)
            }
    }
}
