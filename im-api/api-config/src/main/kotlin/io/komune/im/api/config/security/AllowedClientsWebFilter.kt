package io.komune.im.api.config.security

import io.komune.im.api.config.properties.IMProperties
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

/**
 * Refuses, with 403, a token issued to a client outside `connect.im.security.allowed-clients`.
 *
 * A realm's tokens are all signed by the same issuer, so any of them — including one an end user
 * hands to a third-party application — authenticates here. The authorized party (`azp`) names the
 * client the token was issued to, which Keycloak always sets; `aud` would need an audience mapper on
 * every client. An empty list allows every client, as before. A request without a token is left to
 * the security rules.
 *
 * Runs behind the security chain, which is what installs the identity read here.
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class AllowedClientsWebFilter(properties: IMProperties) : WebFilter {

    private val logger = LoggerFactory.getLogger(AllowedClientsWebFilter::class.java)

    private val allowed = properties.security?.allowedClients.orEmpty()
        .map(String::trim).filter(String::isNotEmpty).toSet()

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if (allowed.isEmpty()) return chain.filter(exchange)
        return ReactiveSecurityContextHolder.getContext()
            .mapNotNull { it.authentication }
            .map { it.isPermitted() }
            .defaultIfEmpty(true)
            .flatMap { permitted -> if (permitted) chain.filter(exchange) else refuse(exchange) }
    }

    private fun Authentication.isPermitted(): Boolean =
        this !is JwtAuthenticationToken || token.getClaimAsString(AUTHORIZED_PARTY) in allowed

    private fun refuse(exchange: ServerWebExchange): Mono<Void> {
        logger.warn("Refused a token issued to a client outside the allowed list on {}", exchange.request.path)
        exchange.response.statusCode = HttpStatus.FORBIDDEN
        return exchange.response.setComplete()
    }

    private companion object {
        const val AUTHORIZED_PARTY = "azp"
    }
}
