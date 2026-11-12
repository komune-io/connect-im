package io.komune.im.commons.auth

import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono
import reactor.util.context.Context

class AuthenticationProviderTest {

    @Test
    fun `without a reactor context there is neither principal nor issuer`() = runTest {
        assertThat(AuthenticationProvider.getPrincipal()).isNull()
        assertThat(AuthenticationProvider.getIssuer()).isEqualTo("null")
        assertThat(AuthenticationProvider.getToken()).isNull()
    }

    @Test
    fun `with an empty security context there is neither principal nor issuer`() = runTest {
        withSecurityContext(Mono.empty()) {
            assertThat(AuthenticationProvider.getPrincipal()).isNull()
            assertThat(AuthenticationProvider.getIssuer()).isEqualTo("null")
        }
    }

    @Test
    fun `with a jwt the principal and its issuer are read`() = runTest {
        val jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .issuer("https://auth.example.org/realms/im")
            .subject("user-id")
            .build()

        withSecurityContext(Mono.just(SecurityContextImpl(JwtAuthenticationToken(jwt)))) {
            assertThat(AuthenticationProvider.getPrincipal()).isEqualTo(jwt)
            assertThat(AuthenticationProvider.getIssuer()).isEqualTo("https://auth.example.org/realms/im")
            assertThat(AuthenticationProvider.getToken()).isEqualTo("token")
        }
    }

    private suspend fun withSecurityContext(securityContext: Mono<SecurityContext>, block: suspend () -> Unit) {
        withContext(ReactorContext(Context.of(SecurityContext::class.java, securityContext))) {
            block()
        }
    }
}
