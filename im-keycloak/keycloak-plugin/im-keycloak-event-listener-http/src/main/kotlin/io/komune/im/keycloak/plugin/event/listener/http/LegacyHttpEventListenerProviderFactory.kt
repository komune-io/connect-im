package io.komune.im.keycloak.plugin.event.listener.http

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds

/**
 * Registers the same listener under its pre-rename id, so realms provisioned by an older
 * release keep delivering events after an upgrade.
 *
 * Keycloak resolves event listeners by the id stored in the realm's `eventsListeners`, and
 * a missing id is not an error — events are simply dropped. Removing this class before
 * every realm has been migrated to [KeycloakPluginIds.EVENT_WEBHOOK] would silently stop
 * webhook delivery rather than fail loudly.
 */
@Suppress("DEPRECATION")
class LegacyHttpEventListenerProviderFactory: HttpEventListenerProviderFactory() {
    override fun getId(): String = KeycloakPluginIds.EVENT_WEBHOOK_LEGACY
}
