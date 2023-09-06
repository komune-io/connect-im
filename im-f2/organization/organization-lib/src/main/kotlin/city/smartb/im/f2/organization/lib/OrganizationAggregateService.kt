package city.smartb.im.f2.organization.lib

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.fs.s2.file.domain.features.command.FileUploadCommand
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddKeyCommand
import city.smartb.im.apikey.lib.service.ApiKeyAggregateService
import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.orEmpty
import city.smartb.im.core.organization.api.OrganizationCoreAggregateService
import city.smartb.im.core.organization.domain.command.OrganizationDefineCommand
import city.smartb.im.core.organization.domain.command.OrganizationSetSomeAttributesCommand
import city.smartb.im.core.organization.domain.model.Organization
import city.smartb.im.f2.organization.domain.command.OrganizationCreateCommandDTOBase
import city.smartb.im.f2.organization.domain.command.OrganizationCreatedEvent
import city.smartb.im.f2.organization.domain.command.OrganizationDeleteCommandDTOBase
import city.smartb.im.f2.organization.domain.command.OrganizationDeletedEventDTOBase
import city.smartb.im.f2.organization.domain.command.OrganizationDisableCommand
import city.smartb.im.f2.organization.domain.command.OrganizationDisabledEvent
import city.smartb.im.f2.organization.domain.command.OrganizationUpdateCommand
import city.smartb.im.f2.organization.domain.command.OrganizationUpdatedResult
import city.smartb.im.f2.organization.domain.command.OrganizationUploadLogoCommand
import city.smartb.im.f2.organization.domain.command.OrganizationUploadedLogoEvent
import city.smartb.im.f2.organization.domain.model.OrganizationDTO
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import city.smartb.im.f2.organization.lib.config.OrganizationFsConfig
import city.smartb.im.user.domain.features.command.UserDisableCommand
import city.smartb.im.user.domain.features.command.UserDisabledEvent
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.lib.service.UserAggregateService
import city.smartb.im.user.lib.service.UserFinderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationAggregateService(
    private val apiKeyAggregateService: ApiKeyAggregateService,
    private val organizationCoreAggregateService: OrganizationCoreAggregateService,
    private val organizationFinderService: OrganizationFinderService,
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
) {
    @Autowired(required = false)
    private lateinit var fileClient: FileClient

    suspend fun create(command: OrganizationCreateCommandDTOBase): OrganizationCreatedEvent {
        // TODO check roles targets
        val organizationId = OrganizationDefineCommand(
            id = null,
            identifier = command.name,
            displayName = command.name,
            description = command.description,
            address = command.address,
            roles = command.roles,
            parentOrganizationId = command.parentOrganizationId,
            attributes = command.attributes.orEmpty() + listOfNotNull(
                command.siret?.let { OrganizationDTO::siret.name to it },
                command.website?.let { OrganizationDTO::website.name to it },
            ).toMap().filterValues { it.isNotBlank() },
        ).let { organizationCoreAggregateService.define(it).id }

        if (command.withApiKey) {
            apiKeyAggregateService.addApiKey(ApiKeyOrganizationAddKeyCommand(
                organizationId = organizationId,
                name = "Default"
            ))
        }

        return OrganizationCreatedEvent(
            id = organizationId,
            parentOrganization = command.parentOrganizationId
        )
    }

    suspend fun update(command: OrganizationUpdateCommand): OrganizationUpdatedResult {
        OrganizationDefineCommand(
            id = command.id,
            identifier = command.name,
            displayName = command.name,
            description = command.description,
            address = command.address,
            roles = command.roles,
            attributes = command.attributes.orEmpty() + listOfNotNull(
                command.website?.let { OrganizationDTO::website.name to it },
            ).toMap().filterValues { it.isNotBlank() },
        ).let { organizationCoreAggregateService.define(it).id }

        return OrganizationUpdatedResult(command.id)
    }

    suspend fun uploadLogo(command: OrganizationUploadLogoCommand, file: ByteArray): OrganizationUploadedLogoEvent {
        if (!::fileClient.isInitialized) {
            throw IllegalStateException("FileClient not initialized.")
        }

        val event = fileClient.fileUpload(
            command = FileUploadCommand(
                path = OrganizationFsConfig.pathForOrganization(command.id),
            ),
            file = file
        )

        OrganizationSetSomeAttributesCommand(
            id = command.id,
            attributes = mapOf(OrganizationDTO::logo.name to event.url)
        ).let { organizationCoreAggregateService.setSomeAttributes(it) }

        return OrganizationUploadedLogoEvent(
            id = command.id,
            url = event.url
        )
    }

    suspend fun disable(command: OrganizationDisableCommand): OrganizationDisabledEvent {
        val newAttributes = mapOf(
            Organization::enabled.name to "false",
            OrganizationDTOBase::disabledBy.name to command.disabledBy.orEmpty(),
            OrganizationDTOBase::disabledDate.name to System.currentTimeMillis().toString()
        )

        if (command.anonymize) {
            val organization = organizationFinderService.get(command.id)
            OrganizationDefineCommand(
                id = command.id,
                identifier = "anonymous-${command.id}",
                description = "",
                address = (null as Address?).orEmpty(),
                roles = organization.roles,
                attributes = newAttributes + mapOf(
                    OrganizationDTO::website.name to ""
                )
            ).let { organizationCoreAggregateService.define(it) }
        } else {
            OrganizationSetSomeAttributesCommand(
                id = command.id,
                attributes = newAttributes
            ).let { organizationCoreAggregateService.setSomeAttributes(it) }
        }

        val userEvents = UserPageQuery(
            organizationId = command.id,
            search = null,
            role = null,
            attributes = null,
            withDisabled = false,
            page = 0,
            size = Int.MAX_VALUE
        ).let { userFinderService.userPage(it) }
            .items
            .map { user ->
                UserDisableCommand(
                    id = user.id,
                    disabledBy = command.disabledBy,
                    anonymize = command.anonymize,
                    attributes = command.userAttributes
                ).let { userAggregateService.disable(it) }
            }

        return OrganizationDisabledEvent(
            id = command.id,
            userIds = userEvents.map(UserDisabledEvent::id)
        )
    }

    suspend fun delete(command: OrganizationDeleteCommandDTOBase): OrganizationDeletedEventDTOBase {
        return organizationCoreAggregateService.delete(command)
    }
}
