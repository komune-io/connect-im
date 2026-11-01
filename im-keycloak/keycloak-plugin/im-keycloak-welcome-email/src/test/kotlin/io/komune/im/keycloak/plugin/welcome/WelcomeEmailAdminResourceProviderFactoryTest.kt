package io.komune.im.keycloak.plugin.welcome

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WelcomeEmailAdminResourceProviderFactoryTest {

    @Test
    fun `reports the path segment IM calls it by`() {
        assertThat(WelcomeEmailAdminResourceProviderFactory().id).isEqualTo(KeycloakPluginIds.WELCOME_EMAIL)
    }

    @Test
    fun `is registered for the service loader`() {
        val registration = javaClass.classLoader.getResource(
            "META-INF/services/org.keycloak.services.resources.admin.ext.AdminRealmResourceProviderFactory"
        )
        assertThat(registration).isNotNull()
        assertThat(registration!!.readText().trim())
            .isEqualTo(WelcomeEmailAdminResourceProviderFactory::class.java.name)
    }
}
