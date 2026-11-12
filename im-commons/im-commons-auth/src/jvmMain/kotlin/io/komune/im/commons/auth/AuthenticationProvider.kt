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
    suspend fun getSecurityContext(): SecurityContext? = coroutineContext[ReactorContext.Key]
        ?.context
        ?.get<Mono<SecurityContext>>(SecurityContext::class.java)
        ?.awaitSingleOrNull()

    suspend fun getAuthentication(): JwtAuthenticationToken? {
        val authentication = getSecurityContext()?.authentication ?: return null
        return authentication as JwtAuthenticationToken
    }

    suspend fun getToken(): String? = getAuthentication()?.token?.tokenValue

    suspend fun getPrincipal(): Jwt? = getAuthentication()?.principal?.let { principal -> principal as Jwt }

    suspend fun getIssuer(): String = getPrincipal()?.issuer?.toString() ?: "null"

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
        ?.authorities
        ?.mapNotNull { granted -> granted.authority?.removePrefix(ROLE_PREFIX) }
        .orEmpty()
        .toTypedArray()
}

class TokenInfo(private val authentication: JwtAuthenticationToken?) {

    fun getEmail(): String? = authentication?.token?.getClaimAsString("email")

    fun getClientId(): String? = authentication?.token?.getClaimAsString("clientId")

    fun getUsername(): String? = authentication?.token?.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME)

    fun getGivenName(): String? = authentication?.token?.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME)

    fun getFamilyName(): String? = authentication?.token?.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME)

    fun getName(): String? = authentication?.token?.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME)

    fun getOrganizationId(): String? = authentication?.token?.getClaimAsString("memberOf")

    fun getUserId(): String? = authentication?.token?.getClaimAsString(PREFERRED_USERNAME_CLAIM_NAME)

    fun getAcr(): String? = authentication?.token?.getClaimAsString("acr")

    fun getRoles() = authentication
        ?.authorities
        ?.mapNotNull { granted -> granted.authority?.removePrefix(ROLE_PREFIX) }
        .orEmpty()
        .toTypedArray()

}
