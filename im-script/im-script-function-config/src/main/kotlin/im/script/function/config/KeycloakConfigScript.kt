package im.script.function.config

import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import city.smartb.im.f2.privilege.domain.role.command.RoleDefineCommandDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import f2.spring.exception.NotFoundException
import i2.keycloak.f2.client.domain.ClientId
import im.script.function.config.config.KeycloakConfigParser
import im.script.function.config.config.KeycloakConfigProperties
import im.script.function.config.config.KeycloakUserConfig
import im.script.function.config.config.WebClient
import im.script.function.config.service.ConfigService
import im.script.function.core.model.AuthContext
import im.script.function.core.model.PermissionData
import im.script.function.core.model.RoleData
import im.script.function.core.service.ClientInitService
import im.script.function.core.service.ScriptFinderService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val ORGANIZATION_ID_CLAIM_NAME = "memberOf"

@Service
class KeycloakConfigScript (
    private val clientInitService: ClientInitService,
    private val configService: ConfigService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val scriptFinderService: ScriptFinderService
) {
    private val logger = LoggerFactory.getLogger(KeycloakConfigScript::class.java)

    fun run(authRealm: AuthRealm, configPath: String) {
        val config = KeycloakConfigParser().getConfiguration(configPath)
        run(authRealm, config)
    }

    fun run(authRealm: AuthRealm, config: KeycloakConfigProperties) = runBlocking(AuthContext(authRealm)) {
        logger.info("Verify Realm[${authRealm.realmId}] exists...")
        verifyRealm(authRealm, authRealm.realmId)

        logger.info("Initializing Privileges...")
        initPrivileges(config.permissions, config.roles)
        logger.info("Initialized Privileges")

        logger.info("Initializing Clients...")
        config.webClients.forEach { initWebClient(authRealm, it) }
        config.appClients.forEach { clientInitService.initAppClient(authRealm, authRealm.realmId, it) }
        logger.info("Initialized Client")

        logger.info("Initializing Users...")
        initUsers(authRealm, config.users)
        logger.info("Initialized Users")
    }

    private suspend fun checkIfExists(authRealm: AuthRealm, clientId: ClientId): Boolean {
        return if (scriptFinderService.getClient(authRealm, authRealm.realmId, clientId) != null) {
            logger.info("Client [$clientId] already exists.")
            true
        } else {
            false
        }
    }

    private suspend fun initWebClient(authRealm: AuthRealm, webClient: WebClient) {
        if (!checkIfExists(authRealm, webClient.clientId)) {
            clientInitService.createClient(
                authRealm = authRealm,
                realmId = authRealm.realmId,
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

    private suspend fun initPrivileges(permissions: List<PermissionData>?, roles: List<RoleData>?) {
        initPermissions(permissions)
        initRoles(roles)
    }

    private suspend fun initPermissions(permissions: List<PermissionData>?) = coroutineScope {
        if (permissions.isNullOrEmpty()) {
            return@coroutineScope
        }

        permissions.map { permission ->
            async {
                privilegeFinderService.getPrivilegeOrNull(null, permission.name)
                    ?: privilegeAggregateService.define(permission.toCommand(null))
            }
        }.awaitAll()

        privilegeFinderService.getRoleOrNull(null, ImRole.SUPER_ADMIN.identifier)?.let { superAdminRole ->
            RoleDefineCommandDTOBase(
                identifier = superAdminRole.identifier,
                description = superAdminRole.description,
                targets = superAdminRole.targets,
                locale = superAdminRole.locale,
                bindings = superAdminRole.bindings.mapValues { (_, roles) -> roles.map(RoleDTOBase::identifier) },
                permissions = superAdminRole.permissions + permissions.map(PermissionData::name)
            ).let { privilegeAggregateService.define(it) }
        }
    }

    private suspend fun initRoles(roles: List<RoleData>?) = coroutineScope {
        if (roles.isNullOrEmpty()) {
            return@coroutineScope
        }

        roles.map { role ->
            async {
                privilegeFinderService.getPrivilegeOrNull(null, role.name)
                    ?: privilegeAggregateService.define(role.toCommand(null))
            }
        }.awaitAll()
    }

    private suspend fun verifyRealm(authRealm: AuthRealm, realmId: RealmId) {
        scriptFinderService.getRealm(authRealm, realmId) ?: throw NotFoundException("Realm", realmId)
    }

    private suspend fun initUsers(authRealm: AuthRealm, users: List<KeycloakUserConfig>?) {
        users?.let {
            users.forEach { initUser(authRealm, it) }
        }
    }

    private suspend fun initUser(authRealm: AuthRealm, user: KeycloakUserConfig) {
        scriptFinderService.getUser(authRealm, user.email, authRealm.realmId)
            ?: configService.createUser(
                authRealm = authRealm,
                username = user.username,
                email = user.email,
                firstname = user.firstname,
                lastname = user.lastname,
                isEnable = true,
                password = user.password
            ).let { userId ->
                configService.grantUser(authRealm, userId, user.role)
            }
    }
}
