package city.smartb.im.bdd

import city.smartb.f2.spring.boot.auth.config.WebSecurityConfig
import city.smartb.im.api.config.properties.IMProperties
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
    lateinit var imProperties: IMProperties

    override fun authedContext(): ReactorContext {
        val authedUser = context.authedUser
            ?: return ReactorContext(Context.of(SecurityContext::class.java, Mono.empty<SecurityContext>()))

        val securityContext = mapOf(
            "realm_access" to mapOf(
                "roles" to authedUser.roles
            ),
            "memberOf" to authedUser.memberOf,
            "sub" to authedUser.id,
            "iss" to TODO("FIX that")
        ).let { claims -> Jwt("fake", null, null, mapOf("header" to "fake"), claims) }
            .let { jwt ->
                JwtAuthenticationToken(jwt, authedUser.roles.map { SimpleGrantedAuthority("${WebSecurityConfig.ROLE_PREFIX}$it") })
            }
            .let(::SecurityContextImpl)
        return ReactorContext(Context.of(SecurityContext::class.java, Mono.just(securityContext)))
    }
}
