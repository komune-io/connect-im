package city.smartb.im.script.space.create

import city.smartb.im.commons.auth.AuthContext
import city.smartb.im.commons.utils.ParserUtils
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import city.smartb.im.f2.space.domain.command.SpaceCreateCommand
import city.smartb.im.f2.space.lib.SpaceAggregateService
import city.smartb.im.f2.space.lib.SpaceFinderService
import city.smartb.im.f2.user.domain.command.UserCreateCommandDTOBase
import city.smartb.im.f2.user.lib.UserAggregateService
import city.smartb.im.f2.user.lib.UserFinderService
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.script.core.config.properties.ImScriptSpaceProperties
import city.smartb.im.script.core.config.properties.toAuthRealm
import city.smartb.im.script.core.model.PermissionData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger
import java.util.UUID

@Service
class SpaceCreateScript(
    private val keycloakClientProvider: KeycloakClientProvider,
    private val imScriptSpaceProperties: ImScriptSpaceProperties,
    private val spaceAggregateService: SpaceAggregateService,
    private val spaceFinderService: SpaceFinderService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
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
                initAdmin(adminUser)
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

    private suspend fun initAdmin(properties: AdminUserData) {
        val permissions = privilegeFinderService.listPermissions()

        if (userFinderService.getByEmailOrNull(properties.email) != null) {
            logger.info("User admin already created")
            return
        }

        val password = properties.password ?: UUID.randomUUID().toString()
        logger.info("Creating user admin with password: $password")

        val userId = UserCreateCommandDTOBase(
            email = properties.email,
            password = password,
            givenName = properties.firstName.orEmpty(),
            familyName = properties.lastName.orEmpty(),
            roles = permissions.map(PermissionDTOBase::identifier),
            isPasswordTemporary = false,
            isEmailVerified = true,
            sendVerifyEmail = false,
            sendResetPassword = false
        ).let { userAggregateService.create(it).id }

        val client = keycloakClientProvider.get()
        val realmManagementClientId = client.getClientByIdentifier("realm-management")!!.id
        val realmAdminRole = client.role("realm-admin").toRepresentation()
        client.user(userId).roles().clientLevel(realmManagementClientId).add(listOf(realmAdminRole))
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
