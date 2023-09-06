package i2.keycloak.f2.client.query

import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetResult
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import org.keycloak.representations.idm.ClientRepresentation
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import javax.ws.rs.NotFoundException

class ClientGetFunctionImpl {
    private val logger = LoggerFactory.getLogger(ClientGetFunctionImpl::class.java)

    @Bean
    fun clientGetFunction(): ClientGetFunction = keycloakF2Function { cmd, keycloakClient ->
        try {
            keycloakClient.client(cmd.id)
                .toRepresentation()
                .asModel()
                .asResult()
        } catch (e: NotFoundException) {
            ClientGetResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching client with id[${cmd.id}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }

    private fun ClientRepresentation.asModel(): ClientModel {
        return ClientModel(
            id = this.id,
            clientIdentifier = this.clientId
        )
    }

    private fun ClientModel.asResult(): ClientGetResult {
        return ClientGetResult(this)
    }
}
