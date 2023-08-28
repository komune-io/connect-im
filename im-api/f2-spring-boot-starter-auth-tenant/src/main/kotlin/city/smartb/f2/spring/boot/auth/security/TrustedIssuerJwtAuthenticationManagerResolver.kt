package city.smartb.f2.spring.boot.auth.security

import city.smartb.f2.spring.boot.auth.config.F2TrustedIssuersConfig
import city.smartb.f2.spring.boot.auth.config.WebSecurityConfig
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Function
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class TrustedIssuerJwtAuthenticationManagerResolver(
    val trustedIssuersConfig: F2TrustedIssuersConfig
): ReactiveAuthenticationManagerResolver<String> {

    private val authenticationManagers: MutableMap<String, Mono<ReactiveAuthenticationManager>> = ConcurrentHashMap()

    override fun resolve(issuer: String): Mono<ReactiveAuthenticationManager> {
        if (!issuer.startsWith(trustedIssuersConfig.issuerBaseUri)) return Mono.empty()
        //TODO VERIFY the realm exists
        return this.authenticationManagers.computeIfAbsent(issuer) {
            buildAuthenticationManager(issuer).subscribeOn(Schedulers.boundedElastic())
                .cache(
                    { Duration.ofMillis(Long.MAX_VALUE) },
                    { ex: Throwable? -> Duration.ZERO }
                ) { Duration.ZERO }
        }
    }

    private fun buildAuthenticationManager(issuer: String) = Mono.fromCallable<ReactiveAuthenticationManager> {
        JwtReactiveAuthenticationManager(
            ReactiveJwtDecoders.fromIssuerLocation(issuer)
        ).apply {
            setJwtAuthenticationConverter(jwtAuthenticationConverter())
        }
    }

    fun jwtAuthenticationConverter(): ReactiveJwtAuthenticationConverter {
        return ReactiveJwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(::jwtAuthoritiesConverter)
        }
    }

    fun jwtAuthoritiesConverter(jwt: Jwt): Flux<GrantedAuthority> {
        val realmAccess = jwt.claims["realm_access"] as Map<String, List<String>>?
        return realmAccess?.get("roles").orEmpty().map { role ->
            SimpleGrantedAuthority("${WebSecurityConfig.ROLE_PREFIX}$role")
        }.let { Flux.fromIterable(it) }
    }
}
