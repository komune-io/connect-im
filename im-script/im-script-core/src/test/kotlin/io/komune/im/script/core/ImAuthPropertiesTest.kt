package io.komune.im.script.core

import io.komune.im.script.core.config.properties.ImAuthProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ImAuthPropertiesTest {

    @Test
    fun `neither the client secret nor the password reaches a log`() {
        val properties = ImAuthProperties(
            serverUrl = "https://auth",
            realmId = "master",
            clientId = "im",
            clientSecret = "s3cret",
            username = "admin",
            password = "p4ss",
        )

        assertThat(properties.toString()).doesNotContain("s3cret").doesNotContain("p4ss").contains("username=admin")
    }
}
