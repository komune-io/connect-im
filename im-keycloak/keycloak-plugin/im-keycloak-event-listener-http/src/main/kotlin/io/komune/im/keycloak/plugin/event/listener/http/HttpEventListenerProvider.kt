package io.komune.im.keycloak.plugin.event.listener.http

import io.komune.im.keycloak.plugin.domain.model.KeycloakHttpEvent
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

        val webhookUrl = realm.getAttribute("event-http-webhook")
        if (webhookUrl.isNullOrBlank()) {
            println("No webhook URL configured for realm ${realm.name}")
            return@timed
        }

        val webhookSecret = realm.getAttribute("event-http-webhook-secret")

        if (realm.isEventsEnabled && realm.enabledEventTypesStream.noneMatch { it == event.type.name }) {
            println("Event type [${event.type}] disabled in realm. Not sending.")
            return@timed
        }

        println("Sending to webhook: $webhookUrl")
        WebhookClient.send(webhookUrl, event.toKeycloakHttpEvent(), webhookSecret)
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
        type = type.name,
        realmId = realmId,
        clientId = clientId,
        userId = userId,
        sessionId = sessionId,
        error = error,
        details = details
    )
}
