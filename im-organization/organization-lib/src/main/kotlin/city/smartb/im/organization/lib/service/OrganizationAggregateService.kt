package city.smartb.im.organization.lib.service

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.fs.s2.file.domain.features.command.FileUploadCommand
import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.orEmpty
import city.smartb.im.commons.utils.toJson
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
import city.smartb.im.organization.domain.features.command.OrganizationCreatedEvent
import city.smartb.im.organization.domain.features.command.OrganizationDeleteCommand
import city.smartb.im.organization.domain.features.command.OrganizationDeletedEvent
import city.smartb.im.organization.domain.features.command.OrganizationDisableCommand
import city.smartb.im.organization.domain.features.command.OrganizationDisabledEvent
import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
import city.smartb.im.organization.domain.features.command.OrganizationUpdatedResult
import city.smartb.im.organization.domain.features.command.OrganizationUploadLogoCommand
import city.smartb.im.organization.domain.features.command.OrganizationUploadedLogoEvent
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationDTO
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.organization.lib.config.OrganizationFsConfig
import city.smartb.im.user.domain.features.command.UserDisableCommand
import city.smartb.im.user.domain.features.command.UserDisabledEvent
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.lib.service.UserAggregateService
import city.smartb.im.user.lib.service.UserFinderService
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupDeleteCommand
import i2.keycloak.f2.group.domain.features.command.GroupDeleteFunction
import i2.keycloak.f2.group.domain.features.command.GroupDisableCommand
import i2.keycloak.f2.group.domain.features.command.GroupDisableFunction
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesCommand
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdateCommand
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.NotFoundException

open class OrganizationAggregateService<MODEL: OrganizationDTO>(
    private val authenticationResolver: ImAuthenticationProvider,
    private val groupCreateFunction: GroupCreateFunction,
    private val groupDeleteFunction: GroupDeleteFunction,
    private val groupDisableFunction: GroupDisableFunction,
    private val groupSetAttributesFunction: GroupSetAttributesFunction,
    private val groupUpdateFunction: GroupUpdateFunction,
    private val organizationFinderService: OrganizationFinderService<MODEL>,
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
    private val redisCache: RedisCache,
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

    suspend fun update(command: OrganizationUpdateCommand, mapper: OrganizationMapper<Organization, MODEL>): OrganizationUpdatedResult
            = redisCache.evictIfPresent(CacheName.Organization, command.id) {
        val organization = organizationFinderService.organizationGet(OrganizationGetQuery(command.id), mapper).item
            ?: throw NotFoundException("Organization [${command.id}] not found")

        groupUpdateFunction.invoke(command.toGroupUpdateCommand(  mapper.mapOrganization(organization)))
            .let { result -> OrganizationUpdatedResult(result.id) }
    }

    suspend fun uploadLogo(command: OrganizationUploadLogoCommand, file: ByteArray): OrganizationUploadedLogoEvent
            = redisCache.evictIfPresent(CacheName.Organization, command.id) {
        if (!::fileClient.isInitialized) {
            throw IllegalStateException("FileClient not initialized.")
        }

        val event = fileClient.fileUpload(
            command = FileUploadCommand(
                path = OrganizationFsConfig.pathForOrganization(command.id),
            ),
            file = file
        )

        setAttributes(command.id, mapOf("logo" to event.url))

        OrganizationUploadedLogoEvent(
            id = command.id,
            url = event.url
        )
    }

    suspend fun disable(command: OrganizationDisableCommand, mapper: OrganizationMapper<Organization, MODEL>): OrganizationDisabledEvent
            = redisCache.evictIfPresent(CacheName.Organization, command.id) {
        val auth = authenticationResolver.getAuth()

        val event = GroupDisableCommand(
            id = command.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(groupDisableFunction)

        if (command.anonymize) {
            val organization = organizationFinderService.organizationGet(OrganizationGetQuery(command.id), mapper).item!!

            OrganizationUpdateCommand(
                id = command.id,
                name = "anonymous-${command.id}",
                description = "",
                address = (null as Address?).orEmpty(),
                website = "",
                roles = organization.roles,
                attributes = command.attributes.orEmpty().plus(listOf(
                    Organization::disabledBy.name to command.disabledBy.orEmpty(),
                    Organization::disabledDate.name to System.currentTimeMillis().toString()
                ))
            ).let { update(it, mapper) }
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

        OrganizationDisabledEvent(
            id = event.id,
            userIds = userEvents.map(UserDisabledEvent::id)
        )
    }

    suspend fun delete(command: OrganizationDeleteCommand): OrganizationDeletedEvent
            = redisCache.evictIfPresent(CacheName.Organization, command.id) {
        val auth = authenticationResolver.getAuth()

        val event = GroupDeleteCommand(
            id = command.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(groupDeleteFunction)

        OrganizationDeletedEvent(
            id = event.id
        )
    }

    private suspend fun setAttributes(id: OrganizationId, attributes: Map<String, String>) {
        val auth = authenticationResolver.getAuth()
        GroupSetAttributesCommand(
            id = id,
            attributes = attributes,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(groupSetAttributesFunction)
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
                organization.siret?.let { Organization::siret.name to it },
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
