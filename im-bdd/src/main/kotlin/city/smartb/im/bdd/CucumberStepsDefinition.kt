package city.smartb.im.bdd

import city.smartb.i2.spring.boot.auth.config.WebSecurityConfig
import city.smartb.im.bdd.data.TestContext
import city.smartb.im.commons.exception.ImException
import i2.keycloak.f2.commons.domain.error.I2Exception
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono
import reactor.util.context.Context

open class CucumberStepsDefinition {

    @Autowired
    protected lateinit var context: TestContext

    protected fun String?.orRandom() = this ?: UUID.randomUUID().toString()

    protected fun step(block: suspend () -> Unit) {
        step({ e -> e !is I2Exception && e !is ImException }, block)
    }

    protected fun step(propagateException: (Exception) -> Boolean = { true }, block: suspend () -> Unit) {
        runBlocking(authedContext()) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                context.errors.add(e)
                if (propagateException(e)) {
                    throw e
                }
            }
        }
    }


    fun authedContext(): CoroutineContext {
        val authedUser = context.authedUser
            ?: return ReactorContext(Context.of(SecurityContext::class.java, Mono.empty<SecurityContext>()))

        val securityContext = mapOf(
            "realm_access" to mapOf(
                "roles" to authedUser.roles
            ),
            "memberOf" to authedUser.memberOf,
            "sub" to authedUser.id
        ).let { claims ->
            Jwt("fake", null, null, mapOf("header" to "fake"), claims) }
            .let { jwt ->
                JwtAuthenticationToken(jwt, authedUser.roles.map { SimpleGrantedAuthority("${WebSecurityConfig.ROLE_PREFIX}$it") })
            }
            .let(::SecurityContextImpl)
        return ReactorContext(Context.of(SecurityContext::class.java, Mono.just(securityContext)))
    }
}
