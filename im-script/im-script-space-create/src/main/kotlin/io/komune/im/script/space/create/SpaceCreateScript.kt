package io.komune.im.script.space.create

import io.komune.im.commons.auth.AuthContext
import io.komune.im.commons.auth.ImPermission
import io.komune.im.commons.model.ClientId
import io.komune.im.commons.utils.ParserUtils
import io.komune.im.f2.privilege.domain.permission.model.Permission
import io.komune.im.f2.space.domain.command.SpaceDefineCommand
import io.komune.im.f2.space.domain.command.SpaceSettings
import io.komune.im.f2.user.domain.command.UserCreateCommand
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.model.AppClient
import io.komune.im.script.core.model.FeatureData
import io.komune.im.script.core.model.PermissionData
import io.komune.im.script.core.model.defaultSpaceRootClientId
import io.komune.im.script.core.service.ClientInitService
import io.komune.im.script.core.service.PrivilegeServices
import io.komune.im.script.core.service.SpaceServices
import io.komune.im.script.core.service.UserServices
import io.komune.im.script.space.create.config.AdminUserData
import io.komune.im.script.space.create.config.ClientCredentials
import io.komune.im.script.space.create.config.EventWebhookConfig
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
@Suppress("TooManyFunctions")
class SpaceCreateScript(
    private val keycloakClientProvider: KeycloakClientProvider,
    private val imScriptSpaceProperties: ImScriptSpaceProperties,
    private val spaceServices: SpaceServices,
    private val privilegeServices: PrivilegeServices,
    private val userServices: UserServices,
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

        val newRealmAuth = imScriptSpaceProperties.auth.toAuthRealm(properties.spaceIdentifier)
        withContext(AuthContext(newRealmAuth)) {
            configureRealm(properties)
        }
    }

    private suspend fun configureRealm(properties: SpaceCreateProperties) {
        logger.info("Initializing IM features...")
        val features = initImFeatures()
        logger.info("Initialized IM features: $features")

        logger.info("Initializing IM permissions...")
        val permissions = initImPermissions()
        logger.info("Initialized IM permissions: $permissions")

        logger.info("Granting default IM permissions to every user...")
        grantDefaultRolePermissions()
        logger.info("Granted default IM permissions")

        logger.info("Initializing Client Scopes...")
        initClientScopes()
        logger.info("Initialized Client Scopes")

        logger.info("Initializing Event Webhook...")
        initEventWebhook(properties.eventWebhook)
        logger.info("Initialized Event Webhook")

        logger.info("Initializing Space Root Client...")
        val rootClient = properties.rootClient?.initImClient(properties.spaceIdentifier)
        logger.info("Initialized Space Root Client: ${rootClient ?: "none"}")

        properties.adminUsers?.forEach { adminUser ->
            logger.info("Initializing Admin user [${adminUser.email}]...")
            initAdmin(adminUser)
            logger.info("Initialized Admin")
        }
        logger.info("Initialized Admin users")
    }

    private suspend fun initRealm(properties: SpaceCreateProperties) {
        logger.info("Initializing Space[${properties.spaceIdentifier}]...")
        if (spaceServices.finder.getOrNull(properties.spaceIdentifier) != null) {
            logger.info("Space[${properties.spaceIdentifier}] already created")
        } else {
            logger.info("Creating Space[${properties.spaceIdentifier}]")
            val login = properties.settings?.login
            val _ = spaceServices.aggregate.define(
                SpaceDefineCommand(
                    identifier = properties.spaceIdentifier,
                    displayName = properties.displayName,
                    theme = properties.theme,
                    smtp = properties.smtp,
                    locales = properties.locales ?: listOf("en", "fr"),
                    mfa = properties.mfa,
                    sslRequired = properties.sslRequired,
                    settings = SpaceSettings(
                        registrationAllowed = login?.registrationAllowed,
                        rememberMe = login?.rememberMe,
                        resetPasswordAllowed = login?.resetPasswordAllowed,
                        registrationEmailAsUsername = login?.registrationEmailAsUsername
                    )
                )
            )
        }
        logger.info("Initialized Space")
    }

    private suspend fun initAdmin(properties: AdminUserData) {
        val permissions = privilegeServices.finder.listPermissions()

        if (userServices.finder.getByEmailOrNull(properties.email) != null) {
            logger.info("User admin already created")
            return
        }

        val password = properties.password ?: UUID.randomUUID().toString()
        logger.info("Creating user admin with password: $password")

        val userId = userServices.aggregate.create(
            UserCreateCommand(
                email = properties.email,
                password = password,
                givenName = properties.firstName.orEmpty(),
                familyName = properties.lastName.orEmpty(),
                roles = permissions.map(Permission::identifier),
                isPasswordTemporary = false,
                isEmailVerified = true,
                sendVerifyEmail = false,
                sendResetPassword = false
            )
        ).id

        val client = keycloakClientProvider.getClient()
        val realmManagementClientId = checkNotNull(client.getClientByIdentifier("realm-management")) {
            "Client [realm-management] not found"
        }.id
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
                privilegeServices.finder.getPrivilegeOrNull(feature.name)
                    ?: privilegeServices.aggregate.define(feature.toCommand())
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
                privilegeServices.finder.getPrivilegeOrNull(permission.name)
                    ?: privilegeServices.aggregate.define(permission.toCommand())
            }
        }.awaitAll()
    }

    private suspend fun grantDefaultRolePermissions() {
        // every user and service account of the realm holds its default role
        val client = keycloakClientProvider.getClient()
        val defaultRoleName = client.realm().toRepresentation().defaultRole?.name ?: client.defaultRealmRole
        val defaultRole = client.role(defaultRoleName)
        val held = defaultRole.realmRoleComposites.map { it.name }.toSet()
        val missing = DEFAULT_PERMISSIONS.filterNot(held::contains)
        if (missing.isEmpty()) {
            logger.info("Default role [$defaultRoleName] already holds $DEFAULT_PERMISSIONS")
            return
        }
        logger.info("Adding $missing to default role [$defaultRoleName]")
        defaultRole.addComposites(missing.map { client.role(it).toRepresentation() })
    }

    private suspend fun initClientScopes() {
        val client = keycloakClientProvider.getClient()
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

    private suspend fun initEventWebhook(webhookConfig: EventWebhookConfig?) {
        if (webhookConfig == null || webhookConfig.url.isBlank()) {
            logger.info("No event webhook URL provided, skipping configuration.")
            return
        }

        val client = keycloakClientProvider.getClient()
        val realm = client.realm().toRepresentation()

        val attributes = realm.attributes?.toMutableMap() ?: mutableMapOf()
        attributes["event-http-webhook"] = webhookConfig.url

        if (!webhookConfig.secret.isNullOrBlank()) {
            attributes["event-http-webhook-secret"] = webhookConfig.secret
            logger.info("Configured event webhook with HMAC signature authentication")
        } else {
            logger.warn("No webhook secret provided - webhook will be sent without authentication!")
        }

        // Configure event types
        realm.isEventsEnabled = true
        val listeners = realm.eventsListeners?.toMutableList() ?: mutableListOf()
        @Suppress("DEPRECATION")
        if (listeners.remove(KeycloakPluginIds.EVENT_WEBHOOK_LEGACY)) {
            logger.info(
                "Migrated event listener ${KeycloakPluginIds.EVENT_WEBHOOK_LEGACY}" +
                        " -> ${KeycloakPluginIds.EVENT_WEBHOOK}"
            )
        }
        if (KeycloakPluginIds.EVENT_WEBHOOK !in listeners) {
            listeners.add(KeycloakPluginIds.EVENT_WEBHOOK)
        }
        realm.eventsListeners = listeners

        if (!webhookConfig.events.isNullOrEmpty()) {
            realm.setEnabledEventTypes(webhookConfig.events.map { it.toString() })
            logger.info("Enabled event types: ${webhookConfig.events.joinToString(", ")}")
        } else {
            logger.info("No event types specified - all events will be sent")
        }

        realm.attributes = attributes
        client.realm().update(realm)

        logger.info("Configured event webhook URL: ${webhookConfig.url}")
    }

    private suspend fun ClientCredentials.initImClient(spaceName: String): ClientId = clientInitService.initAppClient(
        AppClient(
            clientId = clientId ?: defaultSpaceRootClientId(spaceName),
            clientSecret = clientSecret,
            roles = emptyList(),
            realmManagementRoles = listOf(
                "manage-realm",
                "manage-users",
                "manage-clients",
                "manage-events",
                "manage-identity-providers",
            )
        )
    )

    companion object {
        val DEFAULT_PERMISSIONS = listOf(ImPermission.IM_MY_USER, ImPermission.IM_MY_ORGANIZATION_READ)
            .map(ImPermission::identifier)
    }
}
