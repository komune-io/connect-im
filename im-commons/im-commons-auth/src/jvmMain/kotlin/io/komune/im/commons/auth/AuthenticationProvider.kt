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

object AuthenticationProvider {
	suspend fun getSecurityContext(): SecurityContext? {
		return coroutineContext[ReactorContext.Key]
			?.context?.get<Mono<SecurityContext>>(SecurityContext::class.java)
			?.awaitSingleOrNull()
	}

	suspend fun getAuthentication(): JwtAuthenticationToken? {
		return getSecurityContext()?.authentication as JwtAuthenticationToken?
	}

	suspend fun getToken(): String? {
		return getAuthentication()?.token?.tokenValue
	}

	suspend fun getPrincipal(): Jwt? {
		return getAuthentication()?.principal as Jwt?
	}

    suspend fun getIssuer(): String {
        return getPrincipal()?.issuer.toString()
    }

    suspend fun getAcr(): String? {
        return getPrincipal()?.getClaimAsString("acr")
    }

	suspend fun info(): TokenInfo {
		return TokenInfo(getAuthentication())
	}

    suspend fun getClientId(): String? {
        return AuthenticationProvider.getPrincipal()?.getClaim<String>(AZP_CLAIM_NAME)
    }

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

    private suspend fun getRoles() = getAuthentication()?.authorities
        ?.map { it.authority.removePrefix("ROLE_") }
        ?.toTypedArray() ?: emptyArray()
}

class TokenInfo(private val authentication: JwtAuthenticationToken?) {

	fun getEmail(): String? {
		return authentication?.token?.getClaimAsString("email")
	}

	fun getClientId(): String? {
		return authentication?.token?.getClaimAsString("clientId")
	}

	fun getUsername(): String? {
		return authentication?.token?.getClaimAsString("preferred_username")
	}

	fun getGivenName(): String? {
		return authentication?.token?.getClaimAsString("preferred_username")
	}

	fun getFamilyName(): String? {
		return authentication?.token?.getClaimAsString("preferred_username")
	}

	fun getName(): String? {
		return authentication?.token?.getClaimAsString("preferred_username")
	}

	fun getOrganizationId(): String? {
		return authentication?.token?.getClaimAsString("memberOf")
	}

	fun getUserId(): String? {
		return authentication?.token?.getClaimAsString("preferred_username")
	}

    fun getAcr(): String? {
        return authentication?.token?.getClaimAsString("acr")
    }

    fun getRoles() = authentication?.authorities
        ?.map { it.authority.removePrefix("ROLE_") }
        ?.toTypedArray() ?: emptyArray()

}
