package i2.keycloak.f2.client.query

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.client.domain.features.query.ClientGetServiceAccountFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetServiceAccountResult
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.query.model.asModel
import i2.keycloak.f2.user.query.service.UserFinderService
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.NotFoundException

@Configuration
class ClientGetServiceAccountFunctionImpl {
    private val logger = LoggerFactory.getLogger(ClientGetServiceAccountFunctionImpl::class.java)

    @Autowired
    private lateinit var userFinderService: UserFinderService

    @Bean
    fun clientGetServiceAccountFunction(): ClientGetServiceAccountFunction = f2Function { query ->
        try {
            val realmClient = AuthRealmClientBuilder().build(query.auth)

            realmClient.getClientResource(query.realmId, query.id)
                .serviceAccountUser
                .asModel { userId -> userFinderService.getRolesComposition(userId, query.realmId, realmClient) }
                .let(::ClientGetServiceAccountResult)

        } catch (e: NotFoundException) {
            ClientGetServiceAccountResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching service account of client [${query.id}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }
}
