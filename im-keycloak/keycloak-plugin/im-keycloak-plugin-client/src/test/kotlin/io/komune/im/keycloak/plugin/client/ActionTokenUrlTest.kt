package io.komune.im.keycloak.plugin.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActionTokenUrlTest {

    private val request = ActionTokenRequest(
        realm = "space",
        clientId = "web app",
        action = "UPDATE_PASSWORD",
        userId = "u-1",
    )

    @Test
    fun `every parameter is URL-encoded`() {
        val url = actionTokenUrl(
            "https://auth.example.org/",
            request.copy(redirectUri = "https://app.example.org/back?step=1&lang=fr"),
        )

        assertThat(url).isEqualTo(
            "https://auth.example.org/realms/space/generate-action-token" +
                "?client_id=web+app&action=UPDATE_PASSWORD&user_id=u-1" +
                "&redirect_uri=https%3A%2F%2Fapp.example.org%2Fback%3Fstep%3D1%26lang%3Dfr"
        )
    }

    @Test
    fun `no redirect_uri when there is none`() {
        assertThat(actionTokenUrl("https://auth.example.org", request)).doesNotContain("redirect_uri")
    }
}
