package city.smartb.im.script.space.create

import city.smartb.im.commons.auth.AuthContext
import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.utils.ParserUtils
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import city.smartb.im.f2.space.domain.command.SpaceCreateCommand
import city.smartb.im.f2.space.lib.SpaceAggregateService
import city.smartb.im.f2.space.lib.SpaceFinderService
import city.smartb.im.script.core.config.properties.ImScriptSpaceProperties
import city.smartb.im.script.core.config.properties.toAuthRealm
import city.smartb.im.script.core.model.PermissionData
import city.smartb.im.script.core.service.ScriptFinderService
import city.smartb.im.script.space.create.config.AdminUserData
import city.smartb.im.script.space.create.config.SpaceCreateProperties
import city.smartb.im.script.space.create.service.InitService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger
import java.util.UUID

@Service
class SpaceCreateScript(
    private val imScriptSpaceProperties: ImScriptSpaceProperties,
    private val initService: InitService,
    private val scriptFinderService: ScriptFinderService,
    private val spaceAggregateService: SpaceAggregateService,
    private val spaceFinderService: SpaceFinderService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService
) {
    private val logger by Logger()

    suspend fun run() {
        val jsonPath = imScriptSpaceProperties.jsonCreate ?: return
        val properties = ParserUtils.getConfiguration(jsonPath, SpaceCreateProperties::class.java)

        val masterAuth = imScriptSpaceProperties.auth.toAuthRealm()
        withContext(AuthContext(masterAuth)) {
            logger.info("Initializing Space [${properties.space}]...")
            initRealm(properties)
            logger.info("Initialized Space")
        }

        val newRealmAuth = imScriptSpaceProperties.auth.toAuthRealm(properties.space)
        withContext(AuthContext(newRealmAuth)) {
            logger.info("Initializing IM permissions...")
            initImPermissions()
            logger.info("Initialized IM permissions")

            properties.adminUsers.forEach { adminUser ->
                logger.info("Initializing Admin user [${adminUser.email}]...")
                properties.initAdmin(newRealmAuth, adminUser)
                logger.info("Initialized Admin")
            }
            logger.info("Initialized Admin users")
        }
    }

    private suspend fun initRealm(properties: SpaceCreateProperties) {
        if (spaceFinderService.getOrNull(properties.space) != null) {
            logger.info("Realm already created")
        } else {
            spaceAggregateService.create(SpaceCreateCommand(properties.space))
        }
    }

    private suspend fun SpaceCreateProperties.initAdmin(authRealm: AuthRealm, properties: AdminUserData) {
        val permissions = privilegeFinderService.listPermissions()
        properties.email.let { email ->
            if (scriptFinderService.getUser(authRealm, email, space) != null) {
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
                    realm = space
                )
                initService.grantUser(
                    authRealm = authRealm,
                    id = userId,
                    realm = space,
                    clientId = "realm-management",
                    "realm-admin"
                )
                initService.grantUser(
                    authRealm = authRealm,
                    id = userId,
                    realm = space,
                    clientId = null,
                    roles = permissions.map(PermissionDTOBase::identifier).toTypedArray()
                )
            }
        }
    }

    private suspend fun initImPermissions() = coroutineScope {
        val imPermissions = ParserUtils.getConfiguration("imPermissions.json", Array<PermissionData>::class.java)
        imPermissions.map { permission ->
            async {
                privilegeFinderService.getPrivilegeOrNull(permission.name)
                    ?: privilegeAggregateService.define(permission.toCommand())
            }
        }.awaitAll()
    }
}
