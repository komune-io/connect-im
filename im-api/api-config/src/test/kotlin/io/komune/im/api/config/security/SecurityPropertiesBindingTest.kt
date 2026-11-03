package io.komune.im.api.config.security

import io.komune.im.api.config.properties.SecurityProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource

/**
 * The list as the documented property and environment variable carry it: a binding that came back empty
 * would let every client through.
 */
internal class SecurityPropertiesBindingTest {

    @Test
    fun `the documented comma-separated value binds to the list`() {
        val bound = bind(mapOf("connect.im.security.allowed-clients" to "platform-app, im-root-platform"))
        assertThat(bound.allowedClients.map(String::trim)).containsExactly("platform-app", "im-root-platform")
    }

    @Test
    fun `the camel-case key the environment variable maps to binds too`() {
        val bound = bind(mapOf("connect.im.security.allowedClients" to "platform-app"))
        assertThat(bound.allowedClients).containsExactly("platform-app")
    }

    private fun bind(values: Map<String, String>): SecurityProperties =
        Binder(MapConfigurationPropertySource(values))
            .bind("connect.im.security", SecurityProperties::class.java)
            .get()
}
