package io.komune.im.script.space.create

import io.komune.im.commons.auth.AuthContext
import io.komune.im.commons.model.ClientId
import io.komune.im.commons.utils.ParserUtils
import io.komune.im.f2.privilege.domain.permission.model.Permission
import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import io.komune.im.f2.space.domain.command.SpaceDefineCommand
import io.komune.im.f2.space.domain.command.SpaceSettings
import io.komune.im.f2.space.lib.SpaceAggregateService
import io.komune.im.f2.space.lib.SpaceFinderService
import io.komune.im.f2.user.domain.command.UserCreateCommand
import io.komune.im.f2.user.lib.UserAggregateService
import io.komune.im.f2.user.lib.UserFinderService
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.model.AppClient
import io.komune.im.script.core.model.FeatureData
import io.komune.im.script.core.model.PermissionData
import io.komune.im.script.core.model.defaultSpaceRootClientId
import io.komune.im.script.core.service.ClientInitService
import io.komune.im.script.space.create.config.AdminUserData
import io.komune.im.script.space.create.config.ClientCredentials
import io.komune.im.script.space.create.config.SpaceCreateProperties
import java.util.UUID
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.keycloak.representations.idm.ProtocolMapperRepresentation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

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
    private val clientInitService: ClientInitService,
) {
    private val logger = LoggerFactory.getLogger(SpaceCreateScript::class.java)


    suspend fun run() {
        val jsonPaths = imScriptSpaceProperties.jsonCreate ?: return
        runPaths(jsonPaths)
    }

    suspend fun runPaths(jsonPaths: String) {
        jsonPaths.split(";").forEach { jsonPath ->
            logger.info("****************************************************")
            logger.info("Start processing configuration file [$jsonPath]...")
            val properties = ParserUtils.getConfiguration(jsonPath, SpaceCreateProperties::class.java)

            createScript(properties)
        }
    }

    suspend fun createScript(properties: SpaceCreateProperties) {
        val masterAuth = imScriptSpaceProperties.auth.toAuthRealm()
        withContext(AuthContext(masterAuth)) {
            initRealm(properties)
        }

        val newRealmAuth = imScriptSpaceProperties.auth.toAuthRealm(properties.space)
        withContext(AuthContext(newRealmAuth)) {
            configureRealm(properties)
        }
    }

    private suspend fun configureRealm(properties: SpaceCreateProperties) {
        logger.info("Initializing IM features...")
        initImFeatures()
        logger.info("Initialized IM features")

        logger.info("Initializing IM permissions...")
        initImPermissions()
        logger.info("Initialized IM permissions")

        logger.info("Initializing Client Scopes...")
        initClientScopes()
        logger.info("Initialized Client Scopes")

        logger.info("Initializing Space Root Client...")
        properties.rootClient?.initImClient(properties.space)
        logger.info("Initialized Space Root Client")

        properties.adminUsers?.forEach { adminUser ->
            logger.info("Initializing Admin user [${adminUser.email}]...")
            initAdmin(adminUser)
            logger.info("Initialized Admin")
        }
        logger.info("Initialized Admin users")
    }

    private suspend fun initRealm(properties: SpaceCreateProperties) {
        logger.info("Initializing Space[${properties.space}]...")
        if (spaceFinderService.getOrNull(properties.space) != null) {
            logger.info("Space[${properties.space}] already created")
        } else {
            logger.info("Space create: $${properties}")
            spaceAggregateService.define(
                SpaceDefineCommand(
                    identifier = properties.space,
                    theme = properties.theme,
                    smtp = properties.smtp,
                    locales = properties.locales ?: listOf("en", "fr"),
                    mfa = properties.mfa,
                    settings = SpaceSettings(
                        registrationAllowed = properties.settings?.login?.registrationAllowed,
                        rememberMe = properties.settings?.login?.rememberMe,
                        resetPasswordAllowed = properties.settings?.login?.resetPasswordAllowed
                    )
                )
            )
        }
        logger.info("Initialized Space")
    }

    private suspend fun initAdmin(properties: AdminUserData) {
        val permissions = privilegeFinderService.listPermissions()

        if (userFinderService.getByEmailOrNull(properties.email) != null) {
            logger.info("User admin already created")
            return
        }

        val password = properties.password ?: UUID.randomUUID().toString()
        logger.info("Creating user admin with password: $password")

        val userId = UserCreateCommand(
            email = properties.email,
            password = password,
            givenName = properties.firstName.orEmpty(),
            familyName = properties.lastName.orEmpty(),
            roles = permissions.map(Permission::identifier),
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

    private suspend fun initImFeatures() = coroutineScope {
        val imFeatures = ParserUtils.getConfiguration(
            "imFeatures.json",
            Array<FeatureData>::class.java,
            SpaceCreateScript::class.java.classLoader
        )
        imFeatures.map { feature ->
            async {
                privilegeFinderService.getPrivilegeOrNull(feature.name)
                    ?: privilegeAggregateService.define(feature.toCommand())
            }
        }.awaitAll()
    }

    private suspend fun initImPermissions() = coroutineScope {
        val imPermissions = ParserUtils.getConfiguration(
            "imPermissions.json",
            Array<PermissionData>::class.java,
            SpaceCreateScript::class.java.classLoader
        )
        imPermissions.map { permission ->
            async {
                privilegeFinderService.getPrivilegeOrNull(permission.name)
                    ?: privilegeAggregateService.define(permission.toCommand())
            }
        }.awaitAll()
    }

    private suspend fun initClientScopes() {
        val client = keycloakClientProvider.get()
        val scopesClient = client.realm().clientScopes()
        val rolesScope = scopesClient.findAll()
            .firstOrNull { it.name == "roles" }

        if (rolesScope == null) {
            logger.info("No client scope 'roles' found, skipping initialization.")
            return
        }

        val rolesScopeProtocolMapperClient = scopesClient.get(rolesScope.id).protocolMappers
        rolesScopeProtocolMapperClient.mappers
            .firstOrNull { it.protocolMapper == "oidc-usermodel-realm-role-mapper" }
            ?.let { rolesScopeProtocolMapperClient.delete(it.id) }

        rolesScopeProtocolMapperClient.createMapper(
            ProtocolMapperRepresentation().apply {
                name = "realm roles filtered by feature flags"
                protocol = "openid-connect"
                protocolMapper = KeycloakPluginIds.MAPPER_REALM_ROLE_FEATURE
                config = mapOf(
                    "access.token.claim" to "true",
                    "claim.name" to "realm_access.roles",
                    "id.token.claim" to "false",
                    "jsonType.label" to "String",
                    "multivalued" to "true",
                    "userinfo.token.claim" to "false"
                )
            }
        )
    }

    private suspend fun ClientCredentials.initImClient(spaceName: String): ClientId {
        return AppClient(
            clientId = clientId ?: defaultSpaceRootClientId(spaceName),
            clientSecret = clientSecret,
            roles = listOf(),
            realmManagementRoles = listOf(
                "manage-realm",
                "manage-users",
                "manage-clients",
                "manage-events",
                "manage-identity-providers",
            )
        ).let { clientInitService.initAppClient(it) }

    }
}
