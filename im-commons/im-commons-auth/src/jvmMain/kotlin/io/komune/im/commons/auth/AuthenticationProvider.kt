package io.komune.im.commons.auth

import io.komune.f2.spring.boot.auth.AuthenticationProvider
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono

const val AZP_CLAIM_NAME = "azp"
private const val PREFERRED_USERNAME_CLAIM_NAME = "preferred_username"
private const val ROLE_PREFIX = "ROLE_"

object AuthenticationProvider {
    suspend fun getSecurityContext(): SecurityContext? = coroutineContext[ReactorContext.Key]?.run {
        context.get<Mono<SecurityContext>>(SecurityContext::class.java).awaitSingleOrNull()
    }

    suspend fun getAuthentication(): JwtAuthenticationToken? {
        val authentication = getSecurityContext()?.authentication ?: return null
        return authentication as JwtAuthenticationToken
    }

    suspend fun getToken(): String? = getAuthentication()?.run { token.tokenValue }

    suspend fun getPrincipal(): Jwt? = getAuthentication()?.run { principal as Jwt }

    suspend fun getIssuer(): String = getPrincipal()?.run { issuer?.toString() } ?: "null"

    suspend fun getAcr(): String? = getPrincipal()?.getClaimAsString("acr")

    suspend fun info(): TokenInfo = TokenInfo(getAuthentication())

    suspend fun getClientId(): String? = AuthenticationProvider.getPrincipal()?.getClaim<String>(AZP_CLAIM_NAME)

    suspend fun getAuthedUser(): AuthedUser? = getPrincipal()?.let {
        val tokenInfo = info()

        AuthedUser(
            id = getPrincipal()?.subject.orEmpty(),
            identifier = tokenInfo.getEmail() ?: tokenInfo.getClientId(),
            memberOf = tokenInfo.getOrganizationId(),
            roles = tokenInfo.getRoles(),
            acr = tokenInfo.getAcr()
        )
    }

    private suspend fun getRoles() = getAuthentication()
        ?.run { authorities.mapNotNull { granted -> granted.authority?.removePrefix(ROLE_PREFIX) } }
        .orEmpty()
        .toTypedArray()
}

class TokenInfo(private val authentication: JwtAuthenticationToken?) {

    fun getEmail(): String? = authentication?.run { token.getClaimAsString("email") }

    fun getClientId(): String? = authentication?.run { token.getClaimAsString("clientId") }

    fun getUsername(): String? = authentication?.run { token.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME) }

    fun getGivenName(): String? = authentication?.run { token.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME) }

    fun getFamilyName(): String? = authentication?.run { token.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME) }

    fun getName(): String? = authentication?.run { token.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME) }

    fun getOrganizationId(): String? = authentication?.run { token.getClaimAsString("memberOf") }

    fun getUserId(): String? = authentication?.run { token.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME) }

    fun getAcr(): String? = authentication?.run { token.getClaimAsString("acr") }

    fun getRoles() = authentication
        ?.run { authorities.mapNotNull { granted -> granted.authority?.removePrefix(ROLE_PREFIX) } }
        .orEmpty()
        .toTypedArray()

}
