package city.smartb.im.apikey.lib.service

import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddKeyCommand
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddedEvent
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveCommand
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveEvent
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.commons.utils.toJson
import city.smartb.im.core.client.api.ClientCoreAggregateService
import city.smartb.im.core.client.domain.command.ClientCreateCommand
import city.smartb.im.core.organization.api.OrganizationCoreAggregateService
import city.smartb.im.core.organization.api.OrganizationCoreFinderService
import city.smartb.im.core.organization.domain.command.OrganizationSetSomeAttributesCommand
import city.smartb.im.core.user.api.UserCoreAggregateService
import city.smartb.im.core.user.api.service.UserRepresentationTransformer
import city.smartb.im.core.user.domain.command.UserDefineCommand
import city.smartb.im.core.user.domain.model.User
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger
import java.text.Normalizer
import java.util.UUID

@Service
class ApiKeyAggregateService(
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val keycloakClientProvider: KeycloakClientProvider,
    private val organizationCoreAggregateService: OrganizationCoreAggregateService,
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val userCoreAggregateService: UserCoreAggregateService,
    private val userRepresentationTransformer: UserRepresentationTransformer
) {
    private val logger by Logger()

    @Suppress("LongMethod")
    suspend fun addApiKey(
        command: ApiKeyOrganizationAddKeyCommand
    ): ApiKeyOrganizationAddedEvent {
        val organization = organizationCoreFinderService.get(command.organizationId)
        val client = keycloakClientProvider.get()

        val keyIdentifier = Normalizer.normalize("tr-${organization.identifier}-${command.name}-app", Normalizer.Form.NFD)
            .lowercase()
            .replace(Regex("[^a-z0-9]"), "-")
            .replace(Regex("-+"), "-")
        val keySecret = UUID.randomUUID().toString()

        val keyId = ClientCreateCommand(
            identifier = keyIdentifier,
            secret = keySecret,
            isPublicClient = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            authorizationServicesEnabled = false,
            isStandardFlowEnabled = false,
            additionalAccessTokenClaim = listOf(User::memberOf.name),
        ).let { clientCoreAggregateService.create(it).id }

        val newApiKey = ApiKey(
            id = keyId,
            name = command.name,
            identifier = keyIdentifier,
            creationDate = System.currentTimeMillis()
        )
        val apiKeys = organization.apiKeys() + newApiKey
        OrganizationSetSomeAttributesCommand(
            id = organization.id,
            attributes = mapOf(GROUP_API_KEYS_FIELD to apiKeys.toJson())
        ).let { organizationCoreAggregateService.setSomeAttributes(it) }

        val serviceAccountUser = client.client(keyId).serviceAccountUser
        UserDefineCommand(
            id = serviceAccountUser.id,
            memberOf = command.organizationId,
            attributes = mapOf("display_name" to command.name),
            roles = emptyList() // TODO
        ).let { userCoreAggregateService.define(it) }

        return ApiKeyOrganizationAddedEvent(
            organizationId = organization.id,
            id = keyId,
            keyIdentifier = keyIdentifier,
            keySecret = keySecret
        )
    }

    suspend fun removeApiKey(command: ApikeyRemoveCommand): ApikeyRemoveEvent {
        val client = keycloakClientProvider.get()

        val serviceAccountUser = client.client(command.id)
            .serviceAccountUser
            .let { userRepresentationTransformer.transform(it) }

        val organizationId = serviceAccountUser.memberOf!!

        try {
            client.client(command.id).remove()
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
