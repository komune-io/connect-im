package io.komune.im.apikey.lib

import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddKeyCommand
import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddedEvent
import io.komune.im.apikey.domain.command.ApikeyRemoveCommand
import io.komune.im.apikey.domain.command.ApikeyRemoveEvent
import io.komune.im.apikey.domain.model.ApiKeyModel
import io.komune.im.apikey.lib.service.ORGANIZATION_FIELD_API_KEYS
import io.komune.im.apikey.lib.service.apiKeys
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.commons.utils.mapAsync
import io.komune.im.commons.utils.toJson
import io.komune.im.core.client.api.ClientCoreAggregateService
import io.komune.im.core.client.domain.command.ClientCreateCommand
import io.komune.im.core.organization.api.OrganizationCoreAggregateService
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.organization.domain.command.OrganizationCoreSetSomeAttributesCommand
import io.komune.im.core.privilege.api.PrivilegeCoreFinderService
import io.komune.im.core.privilege.api.model.checkTarget
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.core.user.api.UserCoreAggregateService
import io.komune.im.core.user.domain.command.UserCoreDefineCommand
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import org.springframework.stereotype.Service
import java.text.Normalizer
import java.util.UUID
import org.slf4j.LoggerFactory

@Service
class ApiKeyAggregateService(
    private val apiKeyFinderService: ApiKeyFinderService,
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val keycloakClientProvider: KeycloakClientProvider,
    private val privilegeCoreFinderService: PrivilegeCoreFinderService,
    private val organizationCoreAggregateService: OrganizationCoreAggregateService,
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val userCoreAggregateService: UserCoreAggregateService
) {
    private val logger = LoggerFactory.getLogger(ApiKeyAggregateService::class.java)

    @Suppress("LongMethod")
    suspend fun create(
        command: ApiKeyOrganizationAddKeyCommand
    ): ApiKeyOrganizationAddedEvent {
        checkRoles(command.roles)
        val organization = organizationCoreFinderService.get(command.organizationId)
        val client = keycloakClientProvider.get()

        val keyIdentifier = Normalizer.normalize("tr-${organization.identifier}-${command.name}-api-key", Normalizer.Form.NFD)
            .lowercase()
            .replace(Regex("[^a-z0-9]"), "-")
            .replace(Regex("-+"), "-")
        val keySecret = command.secret ?: UUID.randomUUID().toString()

        val keyId = ClientCreateCommand(
            identifier = keyIdentifier,
            secret = keySecret,
            isPublicClient = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            authorizationServicesEnabled = false,
            isStandardFlowEnabled = false,
            additionalAccessTokenClaim = listOf(UserModel::memberOf.name),
        ).let { clientCoreAggregateService.create(it).id }

        val newApiKey = ApiKeyModel(
            id = keyId,
            name = command.name,
            identifier = keyIdentifier,
            roles = command.roles,
            creationDate = System.currentTimeMillis()
        )
        val apiKeys = organization.apiKeys() + newApiKey
        OrganizationCoreSetSomeAttributesCommand(
            id = organization.id,
            attributes = mapOf(ORGANIZATION_FIELD_API_KEYS to apiKeys.toJson())
        ).let { organizationCoreAggregateService.setSomeAttributes(it) }

        val serviceAccountUser = client.client(keyId).serviceAccountUser
        UserCoreDefineCommand(
            id = serviceAccountUser.id,
            memberOf = command.organizationId,
            attributes = mapOf("display_name" to command.name),
            roles = command.roles,
            isApiKey = true
        ).let { userCoreAggregateService.define(it) }

        return ApiKeyOrganizationAddedEvent(
            organizationId = organization.id,
            id = keyId,
            keyIdentifier = keyIdentifier,
            keySecret = keySecret
        )
    }

    suspend fun remove(command: ApikeyRemoveCommand): ApikeyRemoveEvent {
        val client = keycloakClientProvider.get()

        val user = apiKeyFinderService.getUserOfKey(command.id)
        val organizationId = user.memberOf!!

        try {
            client.client(command.id).remove()
        } catch (e: Exception) {
            logger.error("Error while deleting client", e)
        }

        val apiKeys = organizationCoreFinderService.get(organizationId).apiKeys()
        OrganizationCoreSetSomeAttributesCommand(
            id = organizationId,
            attributes = mapOf(ORGANIZATION_FIELD_API_KEYS to apiKeys.filter { it.id != command.id }.toJson())
        ).let { organizationCoreAggregateService.setSomeAttributes(it) }

        return ApikeyRemoveEvent(
            id = command.id,
            organizationId = organizationId
        )
    }

    private suspend fun checkRoles(roles: List<PrivilegeIdentifier>) {
        roles.mapAsync {
            val privilege = privilegeCoreFinderService.getPrivilege(it)
            privilege.checkTarget(RoleTarget.API_KEY)
        }
    }
}
