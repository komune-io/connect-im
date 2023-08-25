package city.smartb.im.apikey.lib.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddKeyCommand
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddedEvent
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveCommand
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveEvent
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyDTO
import city.smartb.im.commons.utils.toJson
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import f2.spring.exception.NotFoundException
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
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import i2.keycloak.f2.group.domain.model.GroupModel
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesCommand
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import org.slf4j.LoggerFactory
import java.text.Normalizer
import java.util.UUID

open class ApiKeyAggregateService<MODEL: ApiKeyDTO>(
    private val authenticationResolver: ImAuthenticationProvider,
    private val clientCreateFunction: ClientCreateFunction,
    private val clientDeleteFunction: ClientDeleteFunction,
    private val clientGetServiceAccountFunction: ClientGetServiceAccountFunction,
    private val clientServiceAccountRolesGrantFunction: ClientServiceAccountRolesGrantFunction,
    private val apikeyFinderService: ApiKeyFinderService<MODEL>,
    private val groupGetFunction: GroupGetFunction,
    private val userSetAttributesFunction: UserSetAttributesFunction,
    private val groupSetAttributesFunction: GroupSetAttributesFunction,
    private val redisCache: RedisCache,
) {

    val logger = LoggerFactory.getLogger(ApiKeyAggregateService::class.java)

    @Suppress("LongMethod")
    suspend fun addApiKey(
        command: ApiKeyOrganizationAddKeyCommand
    ): ApiKeyOrganizationAddedEvent {
        val group = getOrganization(command.organizationId)
        val auth = authenticationResolver.getAuth()
        val clientIdentifier = Normalizer.normalize("tr-${group.name}-${command.name}-app", Normalizer.Form.NFD)
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
        val apiKeys = group.toApiKeys() + newApiKey
        setAttributes(
            id = group.id,
            attributes = mapOf(GROUP_API_KEYS_FIELD to (apiKeys).toJson())
        )

        if (group.roles.assignedRoles.isNotEmpty()) {
            ClientServiceAccountRolesGrantCommand(
                id = clientId,
                roles = group.roles.assignedRoles,
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
            organizationId = group.id,
            id = clientId,
            keyIdentifier = clientIdentifier,
            keySecret = clientSecret
        )
    }

    private suspend fun getOrganization(organizationId: OrganizationId): GroupModel {
        val group = groupGetFunction.invoke(toGroupGetByIdQuery(organizationId)).item
            ?: throw NotFoundException("Organization", organizationId)
        return group
    }

    suspend fun removeApiKey(
        command: ApikeyRemoveCommand,
    ): ApikeyRemoveEvent {
        val auth = authenticationResolver.getAuth()
        val group = getOrganization(command.organizationId)
        ClientGetServiceAccountQuery(
            id = command.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(clientGetServiceAccountFunction)
            .item
            ?.takeIf { it.attributes["memberOf"] == command.organizationId }
            ?: throw NotFoundException("Client", command.id)
        try {
            ClientDeleteCommand(
                id = command.id,
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(clientDeleteFunction)
        } catch (e: NotFoundException) {
            logger.error("Error while deleting client", e)
        } catch (e: Exception) {
            logger.error("Error while deleting client", e)
        }
        val apiKeys = group.toApiKeys()
        setAttributes(
            id = command.organizationId,
            attributes = mapOf(GROUP_API_KEYS_FIELD to apiKeys.filter { it.id != command.id }.toJson())
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
    private suspend fun toGroupGetByIdQuery(organizationId: OrganizationId  ): GroupGetQuery {
        val auth = authenticationResolver.getAuth()
        return GroupGetQuery(
            id = organizationId,
            realmId = auth.realmId,
            auth = auth
        )
    }
}
