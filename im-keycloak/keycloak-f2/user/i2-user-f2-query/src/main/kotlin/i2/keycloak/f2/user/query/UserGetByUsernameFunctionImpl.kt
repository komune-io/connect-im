package i2.keycloak.f2.user.query

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.query.model.asModel
import i2.keycloak.f2.user.query.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetByUsernameFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByUsernameResult
import i2.keycloak.f2.user.domain.model.UserModel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetByUsernameFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	private val logger = LoggerFactory.getLogger(UserGetByUsernameFunctionImpl::class.java)

	@Bean
	fun userGetByUsernameQueryFunction(): UserGetByUsernameFunction = keycloakF2Function { query, client ->
		try {
			client.users(query.realmId)
				.search(query.username)
				.firstOrNull()
				?.asModel { userId -> userFinderService.getRolesComposition(userId, query.realmId, client) }
				.asResult()
		} catch (e: NoSuchElementException) {
			UserGetByUsernameResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with username[${query.username}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel?.asResult(): UserGetByUsernameResult {
		return UserGetByUsernameResult(this)
	}
}
