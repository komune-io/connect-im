package city.smartb.im.apikey.lib.service

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddKeyCommand
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddedEvent
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveCommand
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveEvent
import city.smartb.im.apikey.domain.features.query.ApiKeyGetQuery
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyDTO
import city.smartb.im.commons.exception.NotFoundException
import city.smartb.im.commons.utils.toJson
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.organization.lib.service.OrganizationFinderService
import city.smartb.im.organization.lib.service.OrganizationMapper
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.client.domain.features.command.ClientDeleteCommand
import i2.keycloak.f2.client.domain.features.command.ClientDeleteFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantCommand
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetServiceAccountFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetServiceAccountQuery
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesCommand
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesCommand
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import java.text.Normalizer
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired

open class ApiKeyAggregateService<MODEL: ApiKeyDTO>(
    private val authenticationResolver: ImAuthenticationProvider,
    private val clientCreateFunction: ClientCreateFunction,
    private val clientDeleteFunction: ClientDeleteFunction,
    private val clientGetServiceAccountFunction: ClientGetServiceAccountFunction,
    private val clientServiceAccountRolesGrantFunction: ClientServiceAccountRolesGrantFunction,
    private val apikeyFinderService: ApiKeyFinderService<MODEL>,
    private val organizationFinderService: OrganizationFinderService<Organization>,
    private val organizationMapper: OrganizationMapper<Organization, Organization>,
    private val userSetAttributesFunction: UserSetAttributesFunction,
    private val groupSetAttributesFunction: GroupSetAttributesFunction,
    private val redisCache: RedisCache,
    private var fileClient: FileClient
) {

    @Suppress("LongMethod")
    suspend fun addApiKey(
        command: ApiKeyOrganizationAddKeyCommand
    ): ApiKeyOrganizationAddedEvent {
        val organization = organizationFinderService
                .organizationGet(OrganizationGetQuery(command.organizationId), organizationMapper)
                .item!!

        val auth = authenticationResolver.getAuth()
        val clientIdentifier = Normalizer.normalize("tr-${organization.name}-${command.name}-app", Normalizer.Form.NFD)
            .lowercase()
            .replace(Regex("[^a-z0-9]"), "-")
            .replace(Regex("-+"), "-")
        val clientSecret = UUID.randomUUID().toString()

        val clientId = ClientCreateCommand(
            clientIdentifier = clientIdentifier,
            secret = clientSecret,
            isPublicClient = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            authorizationServicesEnabled = false,
            isStandardFlowEnabled = false,
            protocolMappers = mapOf("memberOf" to command.organizationId),
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(clientCreateFunction).id

        val newApiKey = ApiKey(
            id = clientId,
            name = command.name,
            identifier = clientIdentifier,
            creationDate = System.currentTimeMillis()
        )
        setAttributes(
            id = organization.id,
            attributes = mapOf(Organization::apiKeys.name to (organization.apiKeys + newApiKey).toJson())
        )

        if (organization.roles.isNotEmpty()) {
            ClientServiceAccountRolesGrantCommand(
                id = clientId,
                roles = organization.roles,
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(clientServiceAccountRolesGrantFunction)
        }

        val serviceAccountUser = ClientGetServiceAccountQuery(
            id = clientId,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(clientGetServiceAccountFunction).item!!

        UserSetAttributesCommand(
            id = serviceAccountUser.id,
            attributes = mapOf(
                "memberOf" to command.organizationId,
                "display_name" to command.name
            ),
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(userSetAttributesFunction)

        return ApiKeyOrganizationAddedEvent(
            organizationId = organization.id,
            keyId = clientId,
            keyIdentifier = clientIdentifier,
            keySecret = clientSecret
        )
    }

    suspend fun removeApiKey(
        command: ApikeyRemoveCommand,
        mapper: ApiKeyMapper<ApiKey, MODEL>
    ): ApikeyRemoveEvent {
        val auth = authenticationResolver.getAuth()
        val organization = organizationFinderService
            .organizationGet(OrganizationGetQuery(command.organizationId), organizationMapper)
            .item!!

        val apikey = apikeyFinderService.apikeyGet(ApiKeyGetQuery(id = command.id, organizationId = command.organizationId), mapper).item!!

        ClientGetServiceAccountQuery(
            id = command.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(clientGetServiceAccountFunction)
            .item
            ?.takeIf { it.attributes["memberOf"] == command.id }
            ?: throw NotFoundException("Client", command.id)

        ClientDeleteCommand(
            id = command.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(clientDeleteFunction)

        setAttributes(
            id = command.id,
            attributes = mapOf(Organization::apiKeys.name to organization.apiKeys.filter { it.id != command.id }.toJson())
        )

        return ApikeyRemoveEvent(
            id = command.id,
            organizationId = command.organizationId
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
}
