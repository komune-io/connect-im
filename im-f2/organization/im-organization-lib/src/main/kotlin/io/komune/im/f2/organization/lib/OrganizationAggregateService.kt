package io.komune.im.f2.organization.lib

import io.komune.fs.s2.file.client.FileClient
import io.komune.fs.s2.file.domain.features.command.FileUploadCommand
import io.komune.im.commons.auth.AuthenticationProvider
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.commons.utils.EmptyAddress
import io.komune.im.core.organization.api.OrganizationCoreAggregateService
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.organization.domain.command.OrganizationCoreDefineCommand
import io.komune.im.core.organization.domain.command.OrganizationCoreSetSomeAttributesCommand
import io.komune.im.core.organization.domain.model.OrganizationModel
import io.komune.im.core.privilege.api.PrivilegeCoreFinderService
import io.komune.im.core.privilege.api.model.checkTarget
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.organization.domain.command.OrganizationCreateCommand
import io.komune.im.f2.organization.domain.command.OrganizationCreatedEvent
import io.komune.im.f2.organization.domain.command.OrganizationDeleteCommand
import io.komune.im.f2.organization.domain.command.OrganizationDeletedEvent
import io.komune.im.f2.organization.domain.command.OrganizationDisableCommand
import io.komune.im.f2.organization.domain.command.OrganizationDisabledEvent
import io.komune.im.f2.organization.domain.command.OrganizationUpdateCommand
import io.komune.im.f2.organization.domain.command.OrganizationUpdatedResult
import io.komune.im.f2.organization.domain.command.OrganizationUploadLogoCommand
import io.komune.im.f2.organization.domain.command.OrganizationUploadedLogoEvent
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationDTO
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.organization.lib.config.OrganizationFsConfig
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.user.domain.command.UserDisableCommand
import io.komune.im.f2.user.domain.command.UserDisabledEvent
import io.komune.im.f2.user.domain.command.UserUpdateCommand
import io.komune.im.f2.user.lib.UserAggregateService
import io.komune.im.f2.user.lib.UserFinderService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationAggregateService(
    private val organizationCoreAggregateService: OrganizationCoreAggregateService,
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val privilegeCoreFinderService: PrivilegeCoreFinderService,
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
) {
    @Autowired(required = false)
    private lateinit var fileClient: FileClient

    suspend fun create(command: OrganizationCreateCommand): OrganizationCreatedEvent = coroutineScope {
        checkRoles(command.roles.orEmpty())

        val organizationId = OrganizationCoreDefineCommand(
            id = null,
            identifier = command.name,
            displayName = command.name,
            description = command.description,
            address = command.address,
            roles = command.roles,
            parentOrganizationId = command.parentOrganizationId,
            attributes = command.attributes.orEmpty()
                + listOfNotNull(command.siret?.let { OrganizationDTO::siret.name to it },
                command.website?.let { OrganizationDTO::website.name to it },
                OrganizationDTO::status.name to OrganizationStatus.valueOf(command.status).name
            ).toMap().filterValues { it.isNotBlank() },
        ).let { organizationCoreAggregateService.define(it).id }

        OrganizationCreatedEvent(
            id = organizationId, parentOrganization = command.parentOrganizationId
        )
    }

    suspend fun update(command: OrganizationUpdateCommand): OrganizationUpdatedResult {
        checkRoles(command.roles.orEmpty())

        OrganizationCoreDefineCommand(
            id = command.id,
            identifier = command.name,
            displayName = command.name,
            description = command.description,
            address = command.address,
            roles = command.roles,
            attributes = command.attributes.orEmpty()
                + listOfNotNull(command.website?.let { OrganizationDTO::website.name to it },
                command.status?.let { OrganizationDTO::status.name to OrganizationStatus.valueOf(it).name }).toMap()
                .filterValues { it.isNotBlank() },
        ).let { organizationCoreAggregateService.define(it).id }
        // WCO2-251 - synchronize user roles with the organization's new ones
        refreshUsersOf(command.id)

        return OrganizationUpdatedResult(command.id)
    }

    @Suppress("UseCheckOrError")
    suspend fun uploadLogo(command: OrganizationUploadLogoCommand, file: ByteArray): OrganizationUploadedLogoEvent {
        if (!::fileClient.isInitialized) {
            throw IllegalStateException("FileClient not initialized.")
        }

        val event = fileClient.fileUpload(
            command = FileUploadCommand(
                path = OrganizationFsConfig.pathForOrganization(command.id),
            ), file = file
        )

        OrganizationCoreSetSomeAttributesCommand(
            id = command.id, attributes = mapOf(OrganizationDTO::logo.name to event.url)
        ).let { organizationCoreAggregateService.setSomeAttributes(it) }

        return OrganizationUploadedLogoEvent(
            id = command.id, url = event.url
        )
    }

    suspend fun disable(command: OrganizationDisableCommand): OrganizationDisabledEvent {
        val newAttributes = mapOf(
            OrganizationModel::enabled.name to "false",
            Organization::disabledBy.name to (command.disabledBy
                ?: AuthenticationProvider.getAuthedUser()?.id.orEmpty()),
            Organization::disabledDate.name to System.currentTimeMillis().toString()
        )

        if (command.anonymize) {
            val organization = organizationCoreFinderService.get(command.id)
            OrganizationCoreDefineCommand(
                id = command.id,
                identifier = "anonymous-${command.id}",
                description = "",
                address = EmptyAddress,
                roles = organization.roles,
                attributes = newAttributes + mapOf(
                    OrganizationDTO::website.name to ""
                )
            ).let { organizationCoreAggregateService.define(it) }
        } else {
            OrganizationCoreSetSomeAttributesCommand(
                id = command.id, attributes = newAttributes
            ).let { organizationCoreAggregateService.setSomeAttributes(it) }
        }

        val userEvents = userFinderService.page(
            organizationIds = listOf(command.id)
        ).items.map { user ->
            UserDisableCommand(
                id = user.id,
                disabledBy = command.disabledBy,
                anonymize = command.anonymize,
                attributes = command.userAttributes
            ).let { userAggregateService.disable(it) }
        }

        return OrganizationDisabledEvent(
            id = command.id, userIds = userEvents.map(UserDisabledEvent::id)
        )
    }

    suspend fun delete(command: OrganizationDeleteCommand): OrganizationDeletedEvent {
        return organizationCoreAggregateService.delete(command)
    }

    private suspend fun checkRoles(roles: List<PrivilegeIdentifier>) = coroutineScope {
        roles.map {
            async {
                val privilege = privilegeCoreFinderService.getPrivilege(it)
                privilege.checkTarget(RoleTarget.ORGANIZATION)
            }
        }.awaitAll()
    }

    /**
     * Update each user of the given organization without any modification to trigger
     * their role filters to match the organization's new roles.
     * */
    private suspend fun refreshUsersOf(id: OrganizationId) {
        val users = userFinderService.page(organizationIds = listOf(id))
        users.items.forEach { user ->
            userAggregateService.update(
                UserUpdateCommand(
                    id = user.id,
                    givenName = user.givenName,
                    familyName = user.familyName,
                    address = user.address,
                    phone = user.phone,
                    roles = user.roles.map(Role::identifier),
                    memberOf = id,
                    attributes = user.attributes

                )
            )
        }
    }
}
