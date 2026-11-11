package io.komune.im.keycloak.plugin.event.listener.http

import io.komune.im.keycloak.plugin.domain.model.KeycloakHttpEvent
import kotlinx.coroutines.runBlocking
import org.jboss.logging.Logger
import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.admin.AdminEvent
import org.keycloak.models.KeycloakSession

class HttpEventListenerProvider(
    private val session: KeycloakSession
): EventListenerProvider {
    override fun onEvent(event: Event) {
        timed {
            send(event)
        }
    }

    private suspend fun send(event: Event) {
        logger.info("-----------------")
        logger.info(event.toKeycloakHttpEvent())

        val realm = session.realms().getRealm(event.realmId)
            ?: return

        logger.info("Realm: ${realm.name}")

        val webhookUrl = realm.getAttribute("event-http-webhook")
        when {
            webhookUrl.isNullOrBlank() -> {
                logger.info("No webhook URL configured for realm ${realm.name}")
            }
            realm.isEventsEnabled && realm.enabledEventTypesStream.noneMatch { it == event.type.name } -> {
                logger.info("Event type [${event.type}] disabled in realm. Not sending.")
            }
            else -> {
                logger.info("Sending to webhook: $webhookUrl")
                WebhookClient.send(
                    url = webhookUrl,
                    event = event.toKeycloakHttpEvent(),
                    secret = realm.getAttribute("event-http-webhook-secret"),
                )
            }
        }
    }

    private fun timed(block: suspend () -> Unit) = runBlocking {
        val start = System.currentTimeMillis()
        block()
        logger.info("${System.currentTimeMillis() - start} ms")
    }

    override fun onEvent(event: AdminEvent, includeRepresentation: Boolean) { /* No-op */ }
    override fun close() { /* No-op */ }

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

    private companion object {
        val logger: Logger = Logger.getLogger(HttpEventListenerProvider::class.java)
    }
}
