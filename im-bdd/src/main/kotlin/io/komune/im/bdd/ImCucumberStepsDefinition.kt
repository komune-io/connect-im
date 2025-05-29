package io.komune.im.bdd

import io.komune.f2.spring.boot.auth.config.WebSecurityConfig
import io.komune.im.api.config.properties.IMProperties
import io.komune.im.api.config.properties.toAuthRealm
import io.komune.im.commons.auth.withAuth
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactor.ReactorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono
import reactor.util.context.Context

open class ImCucumberStepsDefinition: s2.bdd.CucumberStepsDefinition() {

    @Autowired
    override lateinit var context: ImTestContext

    @Autowired
    lateinit var keycloakClientProvider: KeycloakClientProvider

    @Autowired
    lateinit var imProperties: IMProperties


    suspend fun <R> withAuth(space: String, block: suspend CoroutineScope.() -> R): R {
        return withAuth(imProperties.keycloak.toAuthRealm(), space, block)
    }

    override fun authedContext(): ReactorContext {
        val authedUser = context.authedUser
            ?: return ReactorContext(Context.of(SecurityContext::class.java, Mono.empty<SecurityContext>()))
        val iss = imProperties.keycloak.url + "/realms/" + context.realmId
        val securityContext = mapOf(
            "realm_access" to mapOf(
                "roles" to authedUser.roles
            ),
            "memberOf" to authedUser.memberOf,
            "sub" to authedUser.id,
            "iss" to iss
        ).let { claims -> Jwt("fake", null, null, mapOf("header" to "fake"), claims) }
            .let { jwt ->
                val authorities = authedUser.roles.map {
                    SimpleGrantedAuthority("${WebSecurityConfig.ROLE_PREFIX}$it")
                }
                JwtAuthenticationToken(
                    jwt,
                    authorities
                )
            }
            .let(::SecurityContextImpl)
        return ReactorContext(Context.of(SecurityContext::class.java, Mono.just(securityContext)))
    }
}
