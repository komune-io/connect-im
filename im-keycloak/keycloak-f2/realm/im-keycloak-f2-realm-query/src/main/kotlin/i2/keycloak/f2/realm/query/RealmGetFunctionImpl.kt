package i2.keycloak.f2.realm.query

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.app.handleNotFoundCause
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.query.RealmGetFunction
import i2.keycloak.f2.realm.domain.features.query.RealmGetResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import javax.ws.rs.ProcessingException
import org.keycloak.representations.idm.RealmRepresentation
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealmGetFunctionImpl {

	private val logger = LoggerFactory.getLogger(RealmGetFunctionImpl::class.java)

	@Bean
	fun realmGetFunction(): RealmGetFunction = f2Function { cmd ->
		try {
			val masterRealm = AuthRealmClientBuilder().build(cmd.authRealm)
			val model = masterRealm.keycloak.realm(cmd.id).toRepresentation().asRealmModel()
			RealmGetResult(model)
		} catch (e: ProcessingException) {
			e.handleNotFoundCause("Error fetching realm with id[${cmd.id}]") {
				RealmGetResult(null)
			}
		} catch (e: NotFoundException) {
			RealmGetResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching realm with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun RealmRepresentation.asRealmModel(): RealmModel {
		return RealmModel(
			id = this.id,
			name = this.displayName,
		)
	}
}
