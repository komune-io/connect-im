package io.komune.im.script.space.config

import f2.spring.exception.ConflictException
import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddKeyCommand
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.apikey.lib.ApiKeyAggregateService
import io.komune.im.commons.auth.AuthContext
import io.komune.im.commons.model.AuthSubRealm
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.utils.ParserUtils
import io.komune.im.commons.utils.mapAsync
import io.komune.im.f2.organization.domain.command.OrganizationCreateCommand
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.organization.lib.OrganizationAggregateService
import io.komune.im.f2.organization.lib.OrganizationFinderService
import io.komune.im.f2.privilege.domain.model.PrivilegeDTO
import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import io.komune.im.f2.space.domain.command.SpaceDefineCommand
import io.komune.im.f2.space.domain.command.SpaceSettings
import io.komune.im.f2.space.lib.SpaceAggregateService
import io.komune.im.f2.space.lib.SpaceFinderService
import io.komune.im.f2.user.domain.command.UserCreateCommand
import io.komune.im.f2.user.lib.UserAggregateService
import io.komune.im.f2.user.lib.UserFinderService
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.init
import io.komune.im.script.core.model.AppClient
import io.komune.im.script.core.model.FeatureData
import io.komune.im.script.core.model.PermissionData
import io.komune.im.script.core.model.RoleData
import io.komune.im.script.core.model.WebClient
import io.komune.im.script.core.service.ClientInitService
import io.komune.im.script.space.config.config.ApiKeyData
import io.komune.im.script.space.config.config.OrganizationData
import io.komune.im.script.space.config.config.SpaceConfigProperties
import io.komune.im.script.space.config.config.UserData
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SpaceConfigScript(
    private val apiKeyAggregateService: ApiKeyAggregateService,
    private val clientInitService: ClientInitService,
    private val imScriptSpaceProperties: ImScriptSpaceProperties,
    private val organizationAggregateService: OrganizationAggregateService,
    private val organizationFinderService: OrganizationFinderService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val spaceFinderService: SpaceFinderService,
    private val spaceAggregateService: SpaceAggregateService,
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
) {
    private val logger = LoggerFactory.getLogger(SpaceConfigScript::class.java)

    suspend fun run() {
        val jsonPaths = imScriptSpaceProperties.jsonConfig ?: return
        runPaths(jsonPaths)
    }

    suspend fun runPaths(jsonPaths: String) {
        jsonPaths.split(";").forEach { jsonPath ->
            logger.info("****************************************************")
            logger.info("Start processing configuration file [$jsonPath]...")
            val properties = ParserUtils.getConfiguration(jsonPath, SpaceConfigProperties::class.java)
            val auth = imScriptSpaceProperties.auth.toAuthRealm(properties.space)
            config(auth, properties)
        }
    }

    suspend fun config(
        auth: AuthSubRealm, properties: SpaceConfigProperties
    ): Any = withContext(AuthContext(auth)) {

        logger.info("Verify Realm[${auth.space}] exists and update it if needed...")
        properties.verifyAndUpdateSpace()

        initFeatures(properties.features)
        initPermissions(properties.permissions)
        initRoles(properties.roles)
        initWebClients(properties.webClients)
        initAppClients(properties.appClients)
        initOrganizations(properties.organizations)
        initUsers(properties.users)
    }

    private suspend fun initFeatures(
        features: List<FeatureData>?
    ) = features.initNotEmpty("Features") { items ->
        items.mapAsync { feature ->
            init("Feature[${feature.name}]", logger, {
                privilegeFinderService.getPrivilegeOrNull(feature.name)
            }, {
                privilegeAggregateService.define(feature.toCommand())
            })
        }
    }

    private suspend fun initPermissions(
        permissions: List<PermissionData>?
    ) = permissions.initNotEmpty("Permissions") { items ->
        items.mapAsync { permission ->
            init("Permission[${permission.name}]", logger, {
                privilegeFinderService.getPrivilegeOrNull(permission.name)
            }, {
                privilegeAggregateService.define(permission.toCommand())
            })
        }
    }

    private suspend fun initWebClients(
        webClients: List<WebClient>?
    ): List<RoleIdentifier> = webClients.initNotEmpty("WebClients") { items ->
        items.map { clientInitService.initWebClient(it) }
    }

    private suspend fun initAppClients(
        appClients: List<AppClient>?
    ): List<RoleIdentifier> = appClients.initNotEmpty("AppClients") { items ->
        items.map { clientInitService.initAppClient(it) }
    }


    private suspend fun initRoles(
        roles: List<RoleData>?
    ): List<RoleIdentifier> = roles.initNotEmpty("Roles") { items ->
        val existingRoles = privilegeFinderService.listRoles().plus(privilegeFinderService.listPermissions())
            .plus(privilegeFinderService.listFeatures()).map(PrivilegeDTO::identifier).toMutableSet()

        val remainingRoles = items.filter { it.name !in existingRoles }.toMutableSet()
        var anyRoleInitialized = true

        while (remainingRoles.isNotEmpty() && anyRoleInitialized) {
            remainingRoles.filter { role -> role.permissions.orEmpty().all { it in existingRoles } }
                .also { anyRoleInitialized = it.isNotEmpty() }
                .mapAsync { role -> privilegeAggregateService.define(role.toCommand()) }.forEach { role ->
                    remainingRoles.removeIf { it.name == role.identifier }
                    existingRoles.add(role.identifier)
                }
        }

        if (remainingRoles.isNotEmpty()) {
            throw IllegalArgumentException(
                "Could not initialize roles [${remainingRoles.joinToString { it.name }}] " +
                    "because some of their permissions do not exist"
            )
        }

        items.map { role ->
            init("Role[${role.name}]", logger, {
                privilegeFinderService.getPrivilegeOrNull(role.name)?.identifier
            }, {
                privilegeAggregateService.define(role.toCommand()).identifier
            })
        }
    }

    private suspend fun SpaceConfigProperties.verifyAndUpdateSpace() {
        val space = spaceFinderService.get(space)
        if (theme != null || locales != null) {
            SpaceDefineCommand(
                identifier = space.identifier,
                theme = theme ?: space.theme,
                smtp = space.smtp,
                locales = locales ?: space.locales,
                mfa = mfa,
                settings = settings?.let {
                    SpaceSettings(
                        registrationAllowed = settings.login.registrationAllowed,
                        rememberMe = settings.login.rememberMe,
                        resetPasswordAllowed = settings.login.resetPasswordAllowed,
                        registrationEmailAsUsername = settings.login.registrationEmailAsUsername
                    )
                }
            ).let { spaceAggregateService.define(it) }
        }
    }

    private suspend fun initOrganizations(
        organizations: List<OrganizationData>?
    ) = organizations.initNotEmpty("Organizations") { items ->
        items.mapAsync { organization ->
            val organizationId = try {
                OrganizationCreateCommand(
                    siret = organization.siret,
                    name = organization.name,
                    description = organization.description,
                    address = organization.address,
                    website = null,
                    roles = organization.roles,
                    parentOrganizationId = null,
                    attributes = organization.attributes,
                    status = OrganizationStatus.VALIDATED.name
                ).let { organizationAggregateService.create(it).id }
            } catch (e: ConflictException) {
                val organizationsCreated = organizationFinderService.page(name = organization.name).items
                if (organizationsCreated.isEmpty() || organizationsCreated.size > 1) {
                    return@mapAsync
                }
                organizationsCreated.first().id
            }

            initUsers(organization.users, organization, organizationId)
            initApiKeys(organization.apiKeys, organization, organizationId)
        }
    }

    private suspend fun <T, R> List<T>?.initNotEmpty(
        objectType: String, organization: OrganizationData? = null, exec: suspend (items: List<T>) -> List<R>
    ): List<R> {
        val suffix = organization?.let { " of Organization [${it.name}]" } ?: ""
        logger.info("Initializing $objectType$suffix...")
        if (this.isNullOrEmpty()) {
            logger.info("No $objectType to initialize")
            return emptyList()
        }
        val result = exec(this)
        logger.info("Initialized $objectType$suffix")
        return result
    }

    private suspend fun initUsers(
        users: List<UserData>?, organization: OrganizationData? = null, organizationId: OrganizationId? = null
    ) = users.initNotEmpty("Users", organization) { items ->
        items.mapAsync { user ->
            init("User[email: ${user.email}]", logger, {
                userFinderService.getByEmailOrNull(user.email)
            }, {
                UserCreateCommand(
                    email = user.email,
                    password = user.password,
                    givenName = user.firstname,
                    familyName = user.lastname,
                    roles = user.roles,
                    memberOf = organizationId,
                    attributes = user.attributes,
                    sendVerifyEmail = false,
                    isEmailVerified = true,
                    isPasswordTemporary = false,
                    sendResetPassword = false
                ).let { userAggregateService.create(it) }
            })
        }
    }

    private suspend fun initApiKeys(
        keys: List<ApiKeyData>?, organization: OrganizationData?, organizationId: OrganizationId
    ): List<ApiKeyId> = keys.initNotEmpty("ApiKeys", organization) {
        // do not make async, as it saves the keys in organization attributes
        keys?.map { key ->
            init("Key[Name: ${key.name}, OrganizationId $organizationId]", logger, {
                apiKeyAggregateService.findByName(key.name, organizationId)?.id
            }, {
                ApiKeyOrganizationAddKeyCommand(
                    organizationId = organizationId, name = key.name, secret = key.secret, roles = key.roles.orEmpty()
                ).let { apiKeyAggregateService.create(it).id }
            })
        } ?: emptyList()
    }
}
