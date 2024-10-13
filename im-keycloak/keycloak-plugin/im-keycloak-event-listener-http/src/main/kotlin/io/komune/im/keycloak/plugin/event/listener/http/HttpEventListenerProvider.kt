package io.komune.im.keycloak.plugin.event.listener.http

import io.komune.im.keycloak.plugin.domain.model.KeycloakHttpEvent
import kotlin.streams.toList as streamToList
import kotlinx.coroutines.runBlocking
import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.admin.AdminEvent
import org.keycloak.models.KeycloakSession

class HttpEventListenerProvider(
    private val session: KeycloakSession
): EventListenerProvider {
    override fun onEvent(event: Event): Unit = timed {
        println("-----------------")
        println(event.toKeycloakHttpEvent())

        val realm = session.realms().getRealm(event.realmId)
            ?: return@timed

        println("Realm: ${realm.name}")

        if (realm.isEventsEnabled && realm.enabledEventTypesStream.noneMatch { it == event.type.name }) {
            println("Event type [${event.type}] disabled in realm. Not sending.")
            return@timed
        }

        val client = session.clients().getClientByClientId(realm, event.clientId)
            ?: return@timed

        println("Client: ${client.clientId}")

        client.protocolMappersStream.streamToList()
            .firstOrNull { it.name == "event-http-webhook" }
            ?.config
            ?.get("claim.value")
            ?.let { url -> WebhookClient.send(url, event.toKeycloakHttpEvent()) }
    }

    private fun timed(block: suspend () -> Unit) = runBlocking {
        val start = System.currentTimeMillis()
        block()
        println("${System.currentTimeMillis() - start} ms")
    }

    override fun onEvent(event: AdminEvent, includeRepresentation: Boolean) {}
    override fun close() {}

    private fun Event.toKeycloakHttpEvent() = KeycloakHttpEvent(
        id = id,
        time = time,
        type = type,
        realmId = realmId,
        clientId = clientId,
        userId = userId,
        sessionId = sessionId,
        error = error,
        details = details
    )
}
