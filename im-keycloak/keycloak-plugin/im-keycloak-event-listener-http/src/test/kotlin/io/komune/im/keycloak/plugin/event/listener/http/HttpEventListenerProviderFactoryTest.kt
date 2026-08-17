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
    fun `implements the SPI factory keycloak loads through ServiceLoader`() {
        assertThat(org.keycloak.provider.ProviderFactory::class.java)
            .isAssignableFrom(HttpEventListenerProviderFactory::class.java)
    }
}
