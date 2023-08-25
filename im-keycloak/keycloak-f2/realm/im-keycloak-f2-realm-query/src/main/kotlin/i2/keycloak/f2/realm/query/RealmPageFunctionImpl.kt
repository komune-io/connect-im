package i2.keycloak.f2.realm.query

import city.smartb.im.infra.keycloak.client.KeycloakClientBuilder
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.app.handleNotFoundCause
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.query.RealmListFunction
import i2.keycloak.f2.realm.domain.features.query.RealmListResult
import javax.ws.rs.ProcessingException
import org.keycloak.representations.idm.RealmRepresentation
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealmPageFunctionImpl {

	private val logger = LoggerFactory.getLogger(RealmPageFunctionImpl::class.java)

	@Bean
	fun realmListFunction(): RealmListFunction = f2Function { cmd ->
		try {
			val masterRealm = KeycloakClientBuilder.openConnection(cmd.auth).forRealm("master")
			val items = masterRealm.keycloak.realms().findAll().map {
                it.asRealmModel()
            }
			RealmListResult(
                items = items,
                total = items.size
            )
		} catch (e: ProcessingException) {
			e.handleNotFoundCause("Error fetching realm page") {
				RealmListResult(emptyList(), 0)
			}
		} catch (e: Exception) {
			val msg = "Error fetching realm page"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception(e)
		}
	}

	private fun RealmRepresentation.asRealmModel(): RealmModel {
		return RealmModel(
			id = this.id,
			name = this.displayName,
		)
	}
}
