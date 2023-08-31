package im.script.function.config

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import city.smartb.im.f2.space.lib.SpaceFinderService
import f2.spring.exception.NotFoundException
import im.script.function.config.config.KeycloakConfigParser
import im.script.function.config.config.KeycloakConfigProperties
import im.script.function.config.config.KeycloakUserConfig
import im.script.function.config.service.ConfigService
import im.script.function.core.model.AuthContext
import im.script.function.core.model.PermissionData
import im.script.function.core.model.RoleData
import im.script.function.core.service.ClientInitService
import im.script.function.core.service.ScriptFinderService
import im.script.gateway.conguration.config.ImScriptConfigProperties
import im.script.gateway.conguration.config.base.toAuthRealm
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class KeycloakConfigScript (
    private val clientInitService: ClientInitService,
    private val configService: ConfigService,
    private val imScriptConfigProperties: ImScriptConfigProperties,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val scriptFinderService: ScriptFinderService,
    private val spaceFinderService: SpaceFinderService
) {
    private val logger = LoggerFactory.getLogger(KeycloakConfigScript::class.java)

    suspend fun run(configPath: String) {
        val config = KeycloakConfigParser().getConfiguration(configPath)
        val auth = imScriptConfigProperties.auth.toAuthRealm(config.realmId)
        withContext(AuthContext(auth)) {
            run(auth, config)
        }
    }

    suspend fun run(authRealm: AuthRealm, config: KeycloakConfigProperties) {
        logger.info("Verify Realm[${config.realmId}] exists...")
        verifyRealm(config.realmId)

        logger.info("Initializing Privileges...")
        initPrivileges(config.permissions, config.roles)
        logger.info("Initialized Privileges")

        logger.info("Initializing Clients...")
        config.webClients.forEach { clientInitService.initWebClient(it) }
        config.appClients.forEach { clientInitService.initAppClient(it) }
        logger.info("Initialized Client")

        logger.info("Initializing Users...")
        initUsers(authRealm, config.users)
        logger.info("Initialized Users")
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
                privilegeFinderService.getPrivilegeOrNull(permission.name)
                    ?: privilegeAggregateService.define(permission.toCommand())
            }
        }.awaitAll()
    }

    private suspend fun initRoles(roles: List<RoleData>?) = coroutineScope {
        if (roles.isNullOrEmpty()) {
            return@coroutineScope
        }

        roles.map { role ->
            async {
                privilegeFinderService.getPrivilegeOrNull(role.name)
                    ?: privilegeAggregateService.define(role.toCommand())
            }
        }.awaitAll()
    }

    private suspend fun verifyRealm(realmId: RealmId) {
        spaceFinderService.getOrNull(realmId)
            ?: throw NotFoundException("Realm", realmId)
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
