package i2.keycloak.f2.client.query

import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierResult
import i2.keycloak.f2.commons.app.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import org.keycloak.representations.idm.ClientRepresentation
import org.springframework.context.annotation.Bean
import javax.ws.rs.NotFoundException

class ClientGetByClientIdentifierFunctionImpl {
    @Bean
    fun clientGetByClientIdentifierFunction(): ClientGetByClientIdentifierFunction = keycloakF2Function { cmd, keycloakClient ->
        try {
            keycloakClient.getClientByClientId(cmd.clientIdentifier)
                ?.asModel()
                .asResult()
        } catch (e: NotFoundException) {
            ClientGetByClientIdentifierResult(null)
        } catch (e: Exception) {
            throw e.asI2Exception("Error fetching client with client identifier[${cmd.clientIdentifier}]")
        }
    }

    private fun ClientRepresentation.asModel(): ClientModel {
        return ClientModel(
            id = this.id,
            clientIdentifier = this.clientId
        )
    }

    private fun ClientModel?.asResult(): ClientGetByClientIdentifierResult {
        return ClientGetByClientIdentifierResult(this)
    }
}
