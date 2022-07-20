package city.smartb.im.organization.api.service

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.fs.s2.file.domain.features.command.FileUploadCommand
import city.smartb.im.api.auth.ImAuthenticationResolver
import city.smartb.im.commons.utils.toJson
import city.smartb.im.organization.api.config.OrganizationFsConfig
import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
import city.smartb.im.organization.domain.features.command.OrganizationCreatedEvent
import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
import city.smartb.im.organization.domain.features.command.OrganizationUpdatedResult
import city.smartb.im.organization.domain.features.command.OrganizationUploadLogoCommand
import city.smartb.im.organization.domain.features.command.OrganizationUploadedLogoEvent
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.model.Organization
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesCommand
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdateCommand
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.ws.rs.NotFoundException

@Service
class OrganizationAggregateService(
    private val authenticationResolver: ImAuthenticationResolver,
    private val groupCreateFunction: GroupCreateFunction,
    private val groupSetAttributesFunction: GroupSetAttributesFunction,
    private val groupUpdateFunction: GroupUpdateFunction,
    private val organizationFinderService: OrganizationFinderService
) {

    @Autowired(required = false)
    private lateinit var fileClient: FileClient

    suspend fun create(command: OrganizationCreateCommand): OrganizationCreatedEvent {
        return groupCreateFunction.invoke(command.toGroupCreateCommand())
            .id
            .let { groupId ->
                OrganizationCreatedEvent(
                    parentOrganization = command.parentOrganizationId,
                    id = groupId
                )
            }
    }

    suspend fun update(command: OrganizationUpdateCommand): OrganizationUpdatedResult {
        val organization = organizationFinderService.organizationGet(OrganizationGetQuery(command.id)).item
            ?: throw NotFoundException("Organization [${command.id}] not found")

        return groupUpdateFunction.invoke(command.toGroupUpdateCommand(organization))
            .let { result -> OrganizationUpdatedResult(result.id) }
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

        val auth = authenticationResolver.getAuth()
        GroupSetAttributesCommand(
            id = command.id,
            attributes = mapOf("logo" to event.url),
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(groupSetAttributesFunction)

        return OrganizationUploadedLogoEvent(
            id = command.id,
            url = event.url
        )
    }

    private suspend fun OrganizationCreateCommand.toGroupCreateCommand(): GroupCreateCommand {
        val auth = authenticationResolver.getAuth()
        return GroupCreateCommand(
            name = name,
            attributes = listOfNotNull(
                siret?.let { Organization::siret.name to it },
                address?.let { Organization::address.name to it.toJson() },
                description?.let { Organization::description.name to it },
                website?.let { Organization::website.name to it },
                Organization::creationDate.name to System.currentTimeMillis().toString()
            ).toMap().plus(attributes.orEmpty()),
            roles = roles ?: emptyList(),
            realmId = auth.realmId,
            auth = auth,
            parentGroupId = parentOrganizationId
        )
    }

    private suspend fun OrganizationUpdateCommand.toGroupUpdateCommand(organization: Organization): GroupUpdateCommand {
        val auth = authenticationResolver.getAuth()
        return GroupUpdateCommand(
            id = id,
            name = name,
            attributes = listOfNotNull(
                Organization::siret.name to organization.siret,
                address?.let { Organization::address.name to it.toJson() },
                description?.let { Organization::description.name to it },
                website?.let { Organization::website.name to it },
                Organization::creationDate.name to organization.creationDate.toString()
            ).toMap().plus(attributes.orEmpty()),
            roles = roles ?: emptyList(),
            realmId = auth.realmId,
            auth = auth
        )
    }
}
