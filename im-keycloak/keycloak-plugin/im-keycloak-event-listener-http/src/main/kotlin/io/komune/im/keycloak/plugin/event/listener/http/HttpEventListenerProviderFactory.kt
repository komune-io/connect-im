package io.komune.im.keycloak.plugin.event.listener.http

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.keycloak.Config
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerProviderFactory
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory

class HttpEventListenerProviderFactory: EventListenerProviderFactory {
    override fun create(session: KeycloakSession): EventListenerProvider {
        return HttpEventListenerProvider(session)
    }
    override fun init(config: Config.Scope) { /* No-op */ }
    override fun postInit(factory: KeycloakSessionFactory) { /* No-op */ }
    override fun getId(): String = KeycloakPluginIds.EVENT_WEBHOOK
    override fun close() { /* No-op */ }
}
