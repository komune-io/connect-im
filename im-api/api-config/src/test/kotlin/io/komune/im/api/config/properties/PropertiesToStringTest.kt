package io.komune.im.api.config.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PropertiesToStringTest {

    @Test
    fun `the keycloak client secret never reaches a log`() {
        val properties = KeycloakProperties(
            url = "https://auth",
            realm = "master",
            clientId = "im",
            clientSecret = "s3cret",
        )

        assertThat(properties.toString()).doesNotContain("s3cret").contains("clientId=im")
    }

    @Test
    fun `the insee token never reaches a log`() {
        val properties = InseeProperties(sireneApi = "https://insee", token = "t0ken")

        assertThat(properties.toString()).doesNotContain("t0ken").contains("sireneApi=https://insee")
    }
}
