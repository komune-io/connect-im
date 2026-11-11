package io.komune.im.bdd

import io.komune.f2.spring.boot.auth.ROLE_PREFIX
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
import s2.bdd.CucumberStepsDefinition

open class ImCucumberStepsDefinition: CucumberStepsDefinition() {

    private var injectedContext: ImTestContext? = null
    private var injectedKeycloakClientProvider: KeycloakClientProvider? = null
    private var injectedImProperties: IMProperties? = null

    override val context: ImTestContext
        get() = checkNotNull(injectedContext) { "ImTestContext has not been injected" }

    val keycloakClientProvider: KeycloakClientProvider
        get() = checkNotNull(injectedKeycloakClientProvider) { "KeycloakClientProvider has not been injected" }

    val imProperties: IMProperties
        get() = checkNotNull(injectedImProperties) { "IMProperties has not been injected" }

    @Autowired
    fun inject(
        context: ImTestContext,
        keycloakClientProvider: KeycloakClientProvider,
        imProperties: IMProperties,
    ) {
        injectedContext = context
        injectedKeycloakClientProvider = keycloakClientProvider
        injectedImProperties = imProperties
    }

    suspend fun <R> withAuth(space: String, block: suspend CoroutineScope.() -> R): R =
        withAuth(imProperties.keycloak.toAuthRealm(), space, block)

    override fun authedContext(): ReactorContext {
        val authedUser = context.authedUser
            ?: return ReactorContext(Context.of(SecurityContext::class.java, Mono.empty<SecurityContext>()))
        val iss = imProperties.keycloak.url + "/realms/" + context.realmId
        val claims = buildMap<String, Any> {
            put(key = "realm_access", value = mapOf("roles" to authedUser.roles))
            authedUser.memberOf?.let { memberOf -> put(key = "memberOf", value = memberOf) }
            put(key = "sub", value = authedUser.id)
            put(key = "iss", value = iss)
        }
        val jwt = Jwt("fake", null, null, mapOf("header" to "fake"), claims)
        val authorities = authedUser.roles.map { role -> SimpleGrantedAuthority("${ROLE_PREFIX}$role") }
        val securityContext = SecurityContextImpl(JwtAuthenticationToken(jwt, authorities))
        return ReactorContext(Context.of(SecurityContext::class.java, Mono.just(securityContext)))
    }
}
