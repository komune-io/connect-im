package io.komune.im.api.config.security

import io.komune.im.api.config.properties.IMProperties
import io.komune.im.api.config.properties.KeycloakProperties
import io.komune.im.api.config.properties.SecurityProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.web.server.MockServerWebExchange
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

/**
 * A token reaches the route only when its client (`azp`) is listed, or when the list is empty.
 */
internal class AllowedClientsWebFilterTest {

    @Test
    fun `a token of an allowed client passes`() {
        val outcome = call(listOf(SERVICE, FRONT), tokenOf(FRONT))
        assertThat(outcome.passed).isTrue()
        assertThat(outcome.status).isNull()
    }

    @Test
    fun `a token of another client is refused with 403`() {
        val outcome = call(listOf(SERVICE), tokenOf(ASSISTANT))
        assertThat(outcome.passed).isFalse()
        assertThat(outcome.status).isEqualTo(HttpStatus.FORBIDDEN)
    }

    @Test
    fun `a token without an authorized party is refused`() {
        val outcome = call(listOf(SERVICE), tokenOf(null))
        assertThat(outcome.passed).isFalse()
        assertThat(outcome.status).isEqualTo(HttpStatus.FORBIDDEN)
    }

    @Test
    fun `an empty list lets every client through`() {
        assertThat(call(emptyList(), tokenOf(ASSISTANT)).passed).isTrue()
    }

    @Test
    fun `blank entries do not turn the list on`() {
        assertThat(call(listOf(" ", ""), tokenOf(ASSISTANT)).passed).isTrue()
    }

    @Test
    fun `entries are trimmed`() {
        assertThat(call(listOf(" $SERVICE "), tokenOf(SERVICE)).passed).isTrue()
    }

    @Test
    fun `a request without a token is left to the security rules`() {
        assertThat(call(listOf(SERVICE), null).passed).isTrue()
    }

    @Test
    fun `an authentication that is not a token is left to the security rules`() {
        val outcome = call(listOf(SERVICE), UsernamePasswordAuthenticationToken("someone", null, emptyList()))
        assertThat(outcome.passed).isTrue()
    }

    private class Outcome(val passed: Boolean, val status: HttpStatus?)

    private fun call(allowed: List<String>, authentication: Authentication?): Outcome {
        val filter = AllowedClientsWebFilter(
            IMProperties(
                organization = null,
                user = null,
                keycloak = KeycloakProperties(
                    url = "http://keycloak", realm = "master", clientId = "im", clientSecret = "s",
                ),
                security = SecurityProperties(allowedClients = allowed),
            )
        )
        val exchange = MockServerWebExchange.from(MockServerHttpRequest.post("/userPage"))
        var passed = false
        val run = filter.filter(exchange, WebFilterChain { Mono.fromRunnable { passed = true } })
        val withIdentity = authentication
            ?.let { run.contextWrite(ReactiveSecurityContextHolder.withAuthentication(it)) }
            ?: run
        withIdentity.block()
        return Outcome(passed, exchange.response.statusCode?.let { HttpStatus.valueOf(it.value()) })
    }

    private fun tokenOf(client: String?): JwtAuthenticationToken {
        val jwt = Jwt.withTokenValue("token").header("alg", "none").subject("user-1")
            .apply { if (client != null) claim("azp", client) }
            .build()
        return JwtAuthenticationToken(jwt)
    }

    private companion object {
        const val SERVICE = "platform-app"
        const val FRONT = "platform-web"
        const val ASSISTANT = "platform-mcp"
    }
}
