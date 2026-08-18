package io.komune.im.keycloak.plugin.action.token

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Keycloak discovers this provider by the id returned from `getId()`, matched against
 * realm configuration. A mismatch is a runtime "provider not found", never a build error.
 */
class ActionTokenRestResourceProviderFactoryTest {

    private val factory = ActionTokenRestResourceProviderFactory()

    @Test
    fun `reports the id keycloak resolves it by`() {
        assertThat(factory.id).isEqualTo(KeycloakPluginIds.ACTION_TOKEN)
    }

    @Test
    fun `implements the SPI factory keycloak loads through ServiceLoader`() {
        assertThat(org.keycloak.provider.ProviderFactory::class.java)
            .isAssignableFrom(ActionTokenRestResourceProviderFactory::class.java)
    }
}
