package io.komune.im.api.gateway.config

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import f2.spring.exception.ConflictException
import f2.spring.exception.ForbiddenAccessException
import f2.spring.exception.NotFoundException
import io.komune.im.api.config.bean.ImAuthenticationProvider
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.MediaType
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    classes = [F2ErrorStatusTest.App::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["f2.tenant.issuer-base-uri="],
)
class F2ErrorStatusTest {

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(basePackageClasses = [F2ErrorStatusTest::class])
    class App {
        @Bean
        fun imAuthenticationProvider() = object : ImAuthenticationProvider {
            override suspend fun getAuth() = error("no realm in this test")
        }

        @Bean
        fun permitAll(http: ServerHttpSecurity): SecurityWebFilterChain = http
            .authorizeExchange { it.anyExchange().permitAll() }
            .csrf { it.disable() }
            .build()

        @Bean
        fun forbiddenFunction(): F2Function<String, String> = f2Function {
            throw ForbiddenAccessException("User [u] is not authorized to get users")
        }

        @Bean
        fun notFoundFunction(): F2Function<String, String> = f2Function {
            throw NotFoundException("User", it)
        }

        @Bean
        fun conflictFunction(): F2Function<String, String> = f2Function {
            throw ConflictException("User", "email", it)
        }

        @Bean
        fun failingFunction(): F2Function<String, String> = f2Function {
            error("unexpected")
        }
    }

    @LocalServerPort
    private var port: Int = 0

    private fun call(function: String): WebTestClient.ResponseSpec =
        WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
            .post().uri("/$function")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("[\"id\"]")
            .exchange()

    @Test
    fun `a refused policy answers 403`() {
        call("forbiddenFunction").expectStatus().isForbidden
            .expectBody().jsonPath("$.message").isEqualTo("User [u] is not authorized to get users")
    }

    @Test
    fun `a missing entity answers 404`() {
        call("notFoundFunction").expectStatus().isNotFound
    }

    @Test
    fun `a conflict answers 409`() {
        call("conflictFunction").expectStatus().isEqualTo(409)
    }

    @Test
    fun `an unexpected error still answers 500`() {
        call("failingFunction").expectStatus().is5xxServerError
    }
}
