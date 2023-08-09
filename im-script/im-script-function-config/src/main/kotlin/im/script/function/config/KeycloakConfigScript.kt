package im.script.function.config

import city.smartb.im.commons.exception.NotFoundException
import im.script.function.config.config.AppClient
import im.script.function.config.config.KeycloakConfigParser
import im.script.function.config.config.KeycloakConfigProperties
import im.script.function.config.config.KeycloakUserConfig
import im.script.function.config.config.WebClient
import im.script.function.config.service.ConfigService
import im.script.function.config.service.ConfigFinderService
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
class KeycloakConfigScript (
    private val keycloakAggregateService: ConfigService,
    private val keycloakFinderService: ConfigFinderService
) {
    private val logger = LoggerFactory.getLogger(KeycloakConfigScript::class.java)

    fun run(authRealm: AuthRealm, configPath: String) {
        val config = KeycloakConfigParser().getConfiguration(configPath)
        run(authRealm, config)
    }

    fun run(authRealm: AuthRealm, config: KeycloakConfigProperties) = runBlocking {
        logger.info("Verify Realm[${authRealm.realmId}] exists...")
        verifyRealm(authRealm, authRealm.realmId)
        logger.info("Initializing Roles...")
        initRoles(authRealm, config.roles, config.roleComposites)
        logger.info("Initialized Roles")

        logger.info("Initializing Clients...")
        config.webClients.forEach { initWebClient(authRealm, it) }
        config.appClients.forEach { initAppClient(authRealm, it) }
        logger.info("Initialized Client")

        logger.info("Initializing Users...")
        initUsers(authRealm, config.users)
        logger.info("Initialized Users")
    }

    private suspend fun checkIfExists(authRealm: AuthRealm, clientId: ClientId): Boolean {
        return if (keycloakFinderService.getClient(authRealm, clientId) != null) {
            logger.info("Client [$clientId] already exists.")
            true
        } else {
            false
        }
    }

    private suspend fun initAppClient(authRealm: AuthRealm, appClient: AppClient) {
        if (!checkIfExists(authRealm, appClient.clientId)) {
            val secret = appClient.clientSecret ?: UUID.randomUUID().toString()
            keycloakAggregateService.createClient(
                authRealm = authRealm,
                identifier = appClient.clientId,
                secret = secret,
                isPublic = false,
                isServiceAccountsEnabled = true,
                isDirectAccessGrantsEnabled = false,
                isStandardFlowEnabled = false
            ).let { clientId ->
                appClient.roles?.toList()?.let { list ->
                    keycloakAggregateService.grantClient(
                        authRealm = authRealm,
                        id = clientId,
                        roles = list
                    )
                }
                appClient.realmManagementRoles?.toList()?.let { list ->
                    keycloakAggregateService.grantRealmManagementClient(
                        authRealm = authRealm,
                        id = appClient.clientId,
                        roles = list,
                    )
                }
            }
            logger.info("App secret: $secret")
        }
    }

    private suspend fun initWebClient(authRealm: AuthRealm, webClient: WebClient) {
        if (!checkIfExists(authRealm, webClient.clientId)) {
            keycloakAggregateService.createClient(
                authRealm = authRealm,
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

    private suspend fun initRoles(authRealm: AuthRealm, roles: List<RoleName>?, roleComposites: Map<RoleName, List<RoleName>>?) {
        roles?.let {
            roles.forEach { role ->
                initRoleWithComposites(authRealm, role)
            }
            addCompositesToAdmin(authRealm, roles.filter { it != SUPER_ADMIN_ROLE })
        }

        roleComposites?.let {
            roleComposites.forEach { roleComposite ->
                initRoleWithComposites(authRealm, roleComposite.key, roleComposite.value)
            }
        }
    }

    private suspend fun verifyRealm(authRealm: AuthRealm, realmId: RealmId) {
        keycloakFinderService.getRealm(authRealm, realmId) ?: throw NotFoundException("Realm", realmId)
    }
    private suspend fun initRoleWithComposites(authRealm: AuthRealm, role: RoleName, composites: List<RoleName> = emptyList()) {
        keycloakFinderService.getRole(authRealm, role)
            ?: keycloakAggregateService.createRole(authRealm, role)

        if (composites.isNotEmpty()) {
            keycloakAggregateService.addRoleComposites(authRealm, role, composites)
        }
    }

    private suspend fun addCompositesToAdmin(authRealm: AuthRealm, composites: List<RoleName>) {
        keycloakAggregateService.addRoleComposites(authRealm, SUPER_ADMIN_ROLE, composites)
    }

    private suspend fun initUsers(authRealm: AuthRealm, users: List<KeycloakUserConfig>?) {
        users?.let {
            users.forEach { initUser(authRealm, it) }
        }
    }

    private suspend fun initUser(authRealm: AuthRealm, user: KeycloakUserConfig) {
        keycloakFinderService.getUser(authRealm, user.email)
            ?: keycloakAggregateService.createUser(
                authRealm = authRealm,
                username = user.username,
                email = user.email,
                firstname = user.firstname,
                lastname = user.lastname,
                isEnable = true,
                password = user.password
            ).let { userId ->
                keycloakAggregateService.grantUser(authRealm, userId, user.role)
            }
    }
}
