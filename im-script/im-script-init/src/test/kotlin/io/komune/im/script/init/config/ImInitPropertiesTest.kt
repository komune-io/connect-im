package io.komune.im.script.init.config

import io.komune.im.script.core.model.ClientCredentials
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ImInitPropertiesTest {

    private val credentials = ClientCredentials(clientId = "root", clientSecret = "secret")

    @Test
    fun `rootClient supersedes the deprecated imMasterClient`() {
        val properties = ImInitProperties(
            rootClient = credentials,
            sslRequired = null,
            imMasterClient = null
        )

        assertThat(properties.rootClient).isEqualTo(credentials)
        assertThat(properties.imMasterClient).isNull()
    }

    @Test
    fun `the deprecated field is still readable for existing configurations`() {
        @Suppress("DEPRECATION")
        val properties = ImInitProperties(rootClient = null, imMasterClient = credentials)

        @Suppress("DEPRECATION")
        assertThat(properties.imMasterClient).isEqualTo(credentials)
    }

    @Test
    fun `sslRequired defaults to null so the realm default is left alone`() {
        val properties = ImInitProperties(rootClient = credentials, imMasterClient = null)

        assertThat(properties.sslRequired).isNull()
    }

    @Test
    fun `sslRequired carries the keycloak value verbatim`() {
        val properties = ImInitProperties(
            rootClient = credentials, sslRequired = "external", imMasterClient = null
        )

        assertThat(properties.sslRequired).isEqualTo("external")
    }
}
