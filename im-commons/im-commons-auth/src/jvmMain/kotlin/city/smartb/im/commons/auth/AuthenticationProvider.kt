package city.smartb.im.commons.auth

import city.smartb.f2.spring.boot.auth.AuthenticationProvider
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono
import kotlin.coroutines.coroutineContext

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

	suspend fun info(): TokenInfo {
		return TokenInfo(getAuthentication())
	}

    suspend fun getClientId(): String? {
        return AuthenticationProvider.getPrincipal()?.getClaim<String>(AZP_CLAIM_NAME)
    }

    suspend fun getAuthedUser(): AuthedUser? = getPrincipal()?.let {
        AuthedUser(
            id = getPrincipal()!!.subject.orEmpty(),
            memberOf = info().getOrganizationId(),
            roles = getAuthentication()!!.authorities
                .map { it.authority.removePrefix("ROLE_") }
                .toTypedArray()
        )
    }
}

class TokenInfo(private val authentication: JwtAuthenticationToken?) {

	fun getEmail(): String? {
		return authentication?.token?.getClaimAsString("email")
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
}
