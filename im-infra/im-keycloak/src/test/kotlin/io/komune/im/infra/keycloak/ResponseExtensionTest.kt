package io.komune.im.infra.keycloak

import f2.spring.exception.ConflictException
import f2.spring.exception.NotFoundException
import jakarta.ws.rs.core.Response
import java.net.URI
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

/**
 * Keycloak's admin API reports creation results through the Location header and the HTTP
 * status, so this is where a silent misread would turn into a wrong entity id.
 */
class ResponseExtensionTest {

    private fun response(status: Int, location: String? = null, body: String = "") =
        Response.status(status)
            .apply { location?.let { location(URI.create(it)) } }
            .entity(body)
            .build()

    @Test
    fun `the created id is the last path segment of the Location header`() {
        val response = response(201, "http://keycloak/admin/realms/space/users/abc-123")

        assertThat(response.toEntityCreatedId()).isEqualTo("abc-123")
    }

    @Test
    fun `2xx is not a failure`() {
        assertThat(response(200).isFailure()).isFalse()
        assertThat(response(201).isFailure()).isFalse()
        assertThat(response(204).isFailure()).isFalse()
    }

    @Test
    fun `4xx and 5xx are failures`() {
        assertThat(response(400).isFailure()).isTrue()
        assertThat(response(404).isFailure()).isTrue()
        assertThat(response(409).isFailure()).isTrue()
        assertThat(response(500).isFailure()).isTrue()
    }

    @Test
    fun `3xx is not treated as a failure`() {
        // isFailure only rejects below 200 and at or above 400
        assertThat(response(302).isFailure()).isFalse()
    }

    @Test
    fun `a conflict becomes a ConflictException, not a generic error`() {
        assertThatThrownBy { response(409, body = "exists").onCreationFailure("user") }
            .isInstanceOf(ConflictException::class.java)
    }

    @Test
    fun `a not-found becomes a NotFoundException`() {
        assertThatThrownBy { response(404, body = "nope").onCreationFailure("user") }
            .isInstanceOf(NotFoundException::class.java)
    }

    @Test
    fun `any other failure carries the status and body into the message`() {
        assertThatThrownBy { response(500, body = "boom").onCreationFailure("user") }
            .hasMessageContaining("500")
            .hasMessageContaining("boom")
            .hasMessageContaining("user")
    }

    @Test
    fun `handleResponseError returns the id when the response succeeded`() {
        val response = response(201, "http://keycloak/admin/realms/space/users/abc-123")

        assertThat(response.handleResponseError("user")).isEqualTo("abc-123")
    }

    @Test
    fun `handleResponseError throws instead of returning a bogus id on failure`() {
        assertThatThrownBy { response(409, body = "exists").handleResponseError("user") }
            .isInstanceOf(ConflictException::class.java)
    }
}
