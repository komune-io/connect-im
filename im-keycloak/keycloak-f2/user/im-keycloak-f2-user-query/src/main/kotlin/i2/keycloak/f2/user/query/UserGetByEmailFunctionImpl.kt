package i2.keycloak.f2.user.query

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailResult
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.f2.user.query.model.asModel
import i2.keycloak.f2.user.query.service.UserFinderService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetByEmailFunctionImpl {

	private val logger = LoggerFactory.getLogger(UserGetByEmailFunctionImpl::class.java)

	@Bean
	fun userGetByEmailQueryFunction(
		userFinderService: UserFinderService
	): UserGetByEmailFunction = keycloakF2Function { query, client ->
		try {
			client.users().search(null, null, null, query.email, null, null)
				.firstOrNull { it.email == query.email }
				?.asModel { userId -> userFinderService.getRolesComposition(userId, client) }
				.let(::UserGetByEmailResult)
		} catch (e: Exception) {
			val msg = "Error fetching User with email[${query.email}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception(e)
		}
	}

	private fun UserModel?.asResult(): UserGetByEmailResult {
		return UserGetByEmailResult(this)
	}
}
