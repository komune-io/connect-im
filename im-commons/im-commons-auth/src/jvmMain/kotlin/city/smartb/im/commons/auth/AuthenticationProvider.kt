package city.smartb.im.commons.auth

import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono
import kotlin.coroutines.coroutineContext

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

	suspend fun verify(): RoleVerifier {
		return RoleVerifier(getAuthentication())
	}

	suspend fun info(): TokenInfo {
		return TokenInfo(getAuthentication())
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

class RoleVerifier(
	private val token: JwtAuthenticationToken?
) {

	fun hasUserReadPermission(): Boolean {
		return hasPermission(Role.IM_USER_READ)
	}

	fun hasUserWritePermission(): Boolean {
		return hasPermission(Role.IM_USER_WRITE)
	}

	fun hasOrganizationReadPermission(): Boolean {
		return hasPermission(Role.IM_ORGANIZATION_READ)
	}

	fun hasOrganizationWritePermission(): Boolean {
		return hasPermission(Role.IM_ORGANIZATION_WRITE)
	}

	fun hasRoleReadPermission(): Boolean {
		return hasPermission(Role.IM_ROLE_READ)
	}

	fun hasRoleWritePermission(): Boolean {
		return hasPermission(Role.IM_ROLE_WRITE)
	}

	fun isAnonymous() = token == null

	fun isNotAnonymous() = !isAnonymous()

	fun hasPermission(role: Role): Boolean {
		return token?.authorities?.contains(SimpleGrantedAuthority("ROLE_${role.name}")) ?: false
	}
}

suspend fun AuthenticationProvider.getAuthedUser() = AuthedUser(
	id = getPrincipal()?.subject.orEmpty(),
	memberOf = info().getOrganizationId(),
	roles = getAuthentication()?.authorities
		?.map { it.authority.removePrefix("ROLE_") }
		.orEmpty()
		.toTypedArray()
)
