package city.smartb.im.f2.space.lib

import city.smartb.im.api.config.properties.IMProperties
import city.smartb.im.core.client.api.ClientCoreAggregateService
import city.smartb.im.core.client.api.ClientCoreFinderService
import city.smartb.im.core.client.domain.command.ClientGrantClientRolesCommand
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.infra.keycloak.client.buildRealmRepresentation
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.space.domain.features.command.SpaceCreateCommand
import city.smartb.im.space.domain.features.command.SpaceCreatedEvent
import city.smartb.im.space.domain.features.command.SpaceDeleteCommand
import city.smartb.im.space.domain.features.command.SpaceDeletedEvent
import city.smartb.im.space.domain.model.SpaceId
import org.springframework.stereotype.Service

@Service
class SpaceAggregateService(
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
    private val keycloakClientProvider: KeycloakClientProvider,
    private val imProperties: IMProperties,
    private val redisCache: RedisCache
) {
    suspend fun create(command: SpaceCreateCommand): SpaceCreatedEvent {
        val client = keycloakClientProvider.get()

        val realms = buildRealmRepresentation(
            realm = command.identifier,
            smtpServer = imProperties.smtp,
            theme = imProperties.theme,
            locale = null
        )
        client.realms().create(realms)

        val authClientId = clientCoreFinderService.getByIdentifier(client.auth.clientId).id
        val realmClientId = clientCoreFinderService.getByIdentifier("${command.identifier}-realm").id
        val realmClientRoles = clientCoreFinderService.listClientRoles(realmClientId)
        ClientGrantClientRolesCommand(
            id = authClientId,
            providerClientId = realmClientId,
            roles = realmClientRoles
        ).let { clientCoreAggregateService.grantClientRoles(it) }

        return SpaceCreatedEvent(
            identifier = command.identifier,
        )
    }

    suspend fun delete(command: SpaceDeleteCommand): SpaceDeletedEvent = mutate(command.id) {
        val client = keycloakClientProvider.get()
        client.realm(command.id).remove()
        SpaceDeletedEvent(command.id)
    }

    private suspend fun <R> mutate(id: SpaceId, block: suspend () -> R): R = redisCache.evictIfPresent(CacheName.Space, id) { block() }
}
