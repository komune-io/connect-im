package city.smartb.im.apikey.lib.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddKeyCommand
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddedEvent
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveCommand
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveEvent
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.commons.utils.toJson
import city.smartb.im.core.organization.api.OrganizationCoreAggregateService
import city.smartb.im.core.organization.api.OrganizationCoreFinderService
import city.smartb.im.core.organization.domain.command.OrganizationSetSomeAttributesCommand
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
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesCommand
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger
import java.text.Normalizer
import java.util.UUID

@Service
class ApiKeyAggregateService(
    private val authenticationResolver: ImAuthenticationProvider,
    private val clientCreateFunction: ClientCreateFunction,
    private val clientDeleteFunction: ClientDeleteFunction,
    private val clientGetServiceAccountFunction: ClientGetServiceAccountFunction,
    private val clientServiceAccountRolesGrantFunction: ClientServiceAccountRolesGrantFunction,
    private val organizationCoreAggregateService: OrganizationCoreAggregateService,
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val userSetAttributesFunction: UserSetAttributesFunction,
) {
    private val logger by Logger()

    @Suppress("LongMethod")
    suspend fun addApiKey(
        command: ApiKeyOrganizationAddKeyCommand
    ): ApiKeyOrganizationAddedEvent {
        val organization = organizationCoreFinderService.get(command.organizationId)
        val auth = authenticationResolver.getAuth()
        val clientIdentifier = Normalizer.normalize("tr-${organization.identifier}-${command.name}-app", Normalizer.Form.NFD)
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
            realmId = auth.space,
            auth = auth
        ).invokeWith(clientCreateFunction).id

        val newApiKey = ApiKey(
            id = clientId,
            name = command.name,
            identifier = clientIdentifier,
            creationDate = System.currentTimeMillis()
        )
        val apiKeys = organization.apiKeys() + newApiKey
        OrganizationSetSomeAttributesCommand(
            id = organization.id,
            attributes = mapOf(GROUP_API_KEYS_FIELD to apiKeys.toJson())
        ).let { organizationCoreAggregateService.setSomeAttributes(it) }

        if (organization.roles.isNotEmpty()) {
            ClientServiceAccountRolesGrantCommand(
                id = clientId,
                roles = organization.roles,
                realmId = auth.space,
                auth = auth
            ).invokeWith(clientServiceAccountRolesGrantFunction)
        }

        val serviceAccountUser = ClientGetServiceAccountQuery(
            id = clientId,
            realmId = auth.space,
            auth = auth
        ).invokeWith(clientGetServiceAccountFunction).item!!

        UserSetAttributesCommand(
            id = serviceAccountUser.id,
            attributes = mapOf(
                "memberOf" to command.organizationId,
                "display_name" to command.name
            ),
            realmId = auth.space,
            auth = auth
        ).invokeWith(userSetAttributesFunction)

        return ApiKeyOrganizationAddedEvent(
            organizationId = organization.id,
            id = clientId,
            keyIdentifier = clientIdentifier,
            keySecret = clientSecret
        )
    }

    suspend fun removeApiKey(
        command: ApikeyRemoveCommand,
    ): ApikeyRemoveEvent {
        val auth = authenticationResolver.getAuth()
        val organizationId = ClientGetServiceAccountQuery(
            id = command.id,
            realmId = auth.space,
            auth = auth
        ).invokeWith(clientGetServiceAccountFunction).item?.attributes?.get("memberOf")
            ?: throw NotFoundException("Client", command.id)
        try {
            ClientDeleteCommand(
                id = command.id,
                realmId = auth.space,
                auth = auth
            ).invokeWith(clientDeleteFunction)
        } catch (e: NotFoundException) {
            logger.error("Error while deleting client", e)
        } catch (e: Exception) {
            logger.error("Error while deleting client", e)
        }

        val apiKeys = organizationCoreFinderService.get(organizationId).apiKeys()
        OrganizationSetSomeAttributesCommand(
            id = organizationId,
            attributes = mapOf(GROUP_API_KEYS_FIELD to apiKeys.filter { it.id != command.id }.toJson())
        ).let { organizationCoreAggregateService.setSomeAttributes(it) }

        return ApikeyRemoveEvent(
            id = command.id,
            organizationId = organizationId
        )
    }
}
