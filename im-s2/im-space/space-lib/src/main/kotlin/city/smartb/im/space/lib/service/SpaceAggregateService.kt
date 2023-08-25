package city.smartb.im.space.lib.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.api.config.properties.IMProperties
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.space.domain.features.command.SpaceCreateCommand
import city.smartb.im.space.domain.features.command.SpaceCreatedEvent
import city.smartb.im.space.domain.features.command.SpaceDeleteCommand
import city.smartb.im.space.domain.features.command.SpaceDeletedEvent
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.realm.domain.features.command.RealmCreateCommand
import i2.keycloak.f2.realm.domain.features.command.RealmCreateFunction
import i2.keycloak.f2.realm.domain.features.command.RealmDeleteCommand
import i2.keycloak.f2.realm.domain.features.command.RealmDeleteFunction
import org.springframework.stereotype.Service

@Service
open class SpaceAggregateService(
    private val realmCreateFunction: RealmCreateFunction,
    private val realmDeleteFunction: RealmDeleteFunction,

    private val imProperties: IMProperties,
    private val authenticationResolver: ImAuthenticationProvider,
    private val redisCache: RedisCache,
) {

    suspend fun create(command: SpaceCreateCommand): SpaceCreatedEvent {
        val auth = authenticationResolver.getAuth()
        val created = RealmCreateCommand(
            id = command.name,
            theme = imProperties.theme,
            locale = null,
            smtpServer = imProperties.smtp,
            masterRealmAuth = auth,
        ).invokeWith(realmCreateFunction)
        return SpaceCreatedEvent(
            id = created.id,
            name = command.name,
        )
    }

    suspend fun delete(command: SpaceDeleteCommand): SpaceDeletedEvent
            = redisCache.evictIfPresent(CacheName.Space, command.id) {
        val auth = authenticationResolver.getAuth()
        val created = RealmDeleteCommand(
            id = command.id,
            masterRealmAuth = auth,
        ).invokeWith(realmDeleteFunction)
        SpaceDeletedEvent(
            id = created.id,
        )
    }
}
