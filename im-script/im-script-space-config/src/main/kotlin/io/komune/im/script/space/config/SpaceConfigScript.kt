package io.komune.im.script.space.config

import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddKeyCommand
import io.komune.im.apikey.lib.ApiKeyAggregateService
import io.komune.im.commons.auth.AuthContext
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.utils.ParserUtils
import io.komune.im.commons.utils.mapAsync
import io.komune.im.f2.organization.domain.command.OrganizationCreateCommand
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.organization.lib.OrganizationAggregateService
import io.komune.im.f2.privilege.domain.model.PrivilegeDTO
import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import io.komune.im.f2.space.domain.command.SpaceDefineCommand
import io.komune.im.f2.space.lib.SpaceAggregateService
import io.komune.im.f2.space.lib.SpaceFinderService
import io.komune.im.f2.user.domain.command.UserCreateCommand
import io.komune.im.f2.user.lib.UserAggregateService
import io.komune.im.f2.user.lib.UserFinderService
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.model.PermissionData
import io.komune.im.script.core.model.RoleData
import io.komune.im.script.core.service.ClientInitService
import io.komune.im.script.space.config.config.ApiKeyData
import io.komune.im.script.space.config.config.OrganizationData
import io.komune.im.script.space.config.config.SpaceConfigProperties
import io.komune.im.script.space.config.config.UserData
import f2.spring.exception.ConflictException
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.script.core.extentions.init
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SpaceConfigScript (
    private val apiKeyAggregateService: ApiKeyAggregateService,
    private val clientInitService: ClientInitService,
    private val imScriptSpaceProperties: ImScriptSpaceProperties,
    private val organizationAggregateService: OrganizationAggregateService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val spaceFinderService: SpaceFinderService,
    private val spaceAggregateService: SpaceAggregateService,
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService
) {
    private val logger = LoggerFactory.getLogger(SpaceConfigScript::class.java)

    suspend fun run() {
        val jsonPath = imScriptSpaceProperties.jsonConfig ?: return
        val properties = ParserUtils.getConfiguration(jsonPath, SpaceConfigProperties::class.java)

        val auth = imScriptSpaceProperties.auth.toAuthRealm(properties.space)
        withContext(AuthContext(auth)) {
            logger.info("Verify Realm[${properties.space}] exists and update it if needed...")
            properties.verifyAndUpdateSpace()

            logger.info("Initializing Permissions...")
            initPermissions(properties.permissions)
            logger.info("Initialized Permissions")

            logger.info("Initializing Roles...")
            initRoles(properties.roles)
            logger.info("Initialized Roles")

            logger.info("Initializing Clients...")
            properties.webClients.forEach { clientInitService.initWebClient(it) }
            properties.appClients.forEach { clientInitService.initAppClient(it) }
            logger.info("Initialized Client")

            logger.info("Initializing Organizations...")
            initOrganizations(properties.organizations)
            logger.info("Initialized Organizations")

            logger.info("Initializing Users...")
            initUsers(properties.users)
            logger.info("Initialized Users")
        }
    }

    private suspend fun initPermissions(permissions: List<PermissionData>?) {
        if (permissions.isNullOrEmpty()) {
            logger.info("No Permissions to initialize")
            return
        }

        permissions.mapAsync { permission ->
            init("Permission[${permission.name}]", logger, {
                privilegeFinderService.getPrivilegeOrNull(permission.name)
            }, {
                privilegeAggregateService.define(permission.toCommand())
            })
        }
    }

    private suspend fun initRoles(roles: List<RoleData>?): List<RoleIdentifier> {
        if (roles.isNullOrEmpty()) {
            return emptyList()
        }

        val existingRoles = privilegeFinderService.listRoles()
            .plus(privilegeFinderService.listPermissions())
            .map(PrivilegeDTO::identifier)
            .toMutableSet()

        val remainingRoles = roles.filter { it.name !in existingRoles }.toMutableSet()
        var anyRoleInitialized = true

        while (remainingRoles.isNotEmpty() && anyRoleInitialized) {
            remainingRoles.filter { role -> role.permissions.orEmpty().all { it in existingRoles } }
                .also { anyRoleInitialized = it.isNotEmpty() }
                .mapAsync { role -> privilegeAggregateService.define(role.toCommand()) }
                .forEach { role ->
                    remainingRoles.removeIf { it.name == role.identifier }
                    existingRoles.add(role.identifier)
                }
        }

        if (remainingRoles.isNotEmpty()) {
            throw IllegalArgumentException(
                "Could not initialize roles [${remainingRoles.joinToString { it.name }}] because some of their permissions do not exist"
            )
        }

        return roles.map { role ->
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
                locales = locales ?: space.locales
            ).let { spaceAggregateService.define(it) }
        }
    }

    private suspend fun initOrganizations(organizations: List<OrganizationData>?) {
        organizations?.mapAsync { organization ->
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
                return@mapAsync
            }
            logger.info("Initializing Users of Organization [${organization.name}]...")
            initUsers(organization.users, organizationId)
            logger.info("Initialized Users of Organization [${organization.name}]...")
            logger.info("Initializing ApiKeys of Organization [${organization.name}]...")
            initApiKeys(organization.apiKeys, organizationId)
            logger.info("Initialized ApiKeys of Organization [${organization.name}]...")
        }
    }

    private suspend fun initUsers(users: List<UserData>?, organizationId: OrganizationId? = null) {
        users?.mapAsync { user ->
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

    private suspend fun initApiKeys(keys: List<ApiKeyData>?, organizationId: OrganizationId): List<ApiKeyId> {
        if (keys.isNullOrEmpty()) {
            return emptyList()
        }

        // do not make async, as it saves the keys in organization attributes
        return keys.map { key ->
            init("Key[Name: ${key.name}, OrganizationId $organizationId]", logger, {
                apiKeyAggregateService.findByName(key.name, organizationId)?.id
            }, {
                ApiKeyOrganizationAddKeyCommand(
                    organizationId = organizationId,
                    name = key.name,
                    secret = key.secret,
                    roles = key.roles.orEmpty()
                ).let { apiKeyAggregateService.create(it).id }
            })


        }
    }
}
