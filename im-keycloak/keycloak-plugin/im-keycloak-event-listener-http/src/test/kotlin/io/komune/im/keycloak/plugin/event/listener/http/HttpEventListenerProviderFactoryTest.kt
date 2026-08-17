package io.komune.im.keycloak.plugin.event.listener.http

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Keycloak discovers this provider by the id returned from `getId()`, matched against
 * realm configuration. A mismatch is a runtime "provider not found", never a build error.
 */
class HttpEventListenerProviderFactoryTest {

    private val factory = HttpEventListenerProviderFactory()

    @Test
    fun `reports the id keycloak resolves it by`() {
        assertThat(factory.id).isEqualTo(KeycloakPluginIds.EVENT_WEBHOOK)
    }

    @Test
    fun `the legacy factory registers the same listener under the pre-rename id`() {
        @Suppress("DEPRECATION")
        val legacyId = io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds.EVENT_WEBHOOK_LEGACY

        assertThat(LegacyHttpEventListenerProviderFactory().id).isEqualTo(legacyId)
    }

    @Test
    fun `the legacy factory is the same implementation, only the id differs`() {
        assertThat(HttpEventListenerProviderFactory::class.java)
            .isAssignableFrom(LegacyHttpEventListenerProviderFactory::class.java)
        assertThat(LegacyHttpEventListenerProviderFactory().id)
            .isNotEqualTo(HttpEventListenerProviderFactory().id)
    }

    @Test
    fun `implements the SPI factory keycloak loads through ServiceLoader`() {
        assertThat(org.keycloak.provider.ProviderFactory::class.java)
            .isAssignableFrom(HttpEventListenerProviderFactory::class.java)
    }
}
