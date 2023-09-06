package i2.keycloak.f2.user.query

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.query.UserGetFunction
import i2.keycloak.f2.user.domain.features.query.UserGetResult
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.f2.user.query.model.asModel
import i2.keycloak.f2.user.query.service.UserFinderService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.NotFoundException

@Configuration
class UserGetFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	private val logger = LoggerFactory.getLogger(UserGetFunctionImpl::class.java)

	@Bean
	fun userGetByIdQueryFunction(): UserGetFunction = keycloakF2Function { query, client ->
		try {
			client.user(query.id)
				.toRepresentation()
				.asModel { userId -> userFinderService.getRolesComposition(userId, client) }
				.asResult()
		} catch (e: NotFoundException) {
			UserGetResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with id[${query.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception(e)
		}
	}

	private fun UserModel.asResult(): UserGetResult {
		return UserGetResult(this)
	}
}
