package i2.keycloak.f2.client.command

import i2.keycloak.f2.client.domain.features.command.ClientUpdateUrisFunction
import i2.keycloak.f2.client.domain.features.command.ClientUpdatedUrisEvent
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientUpdateUrisFunctionImpl {

    @Bean
    fun clientUpdateUrisFunction(): ClientUpdateUrisFunction = keycloakF2Function { cmd, keycloakClient ->
        try {
            val clientResource = keycloakClient.client(cmd.id)

            val clientRep = clientResource.toRepresentation()
            clientRep.baseUrl = cmd.baseUrl
            clientRep.redirectUris = cmd.redirectUris
            clientRep.rootUrl = cmd.rootUrl

            clientResource.update(clientRep)

            ClientUpdatedUrisEvent(cmd.id)
        } catch (e: Exception) {
            throw I2ApiError(
                description = "Realm[${cmd.realmId}] Client[${cmd.id}] Error updating URIs",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
