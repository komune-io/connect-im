package i2.keycloak.f2.client.command

import i2.keycloak.f2.client.domain.features.command.ClientDeleteFunction
import i2.keycloak.f2.client.domain.features.command.ClientDeletedEvent
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientDeleteFunctionImpl {

    @Bean
    fun clientDeleteFunction(): ClientDeleteFunction = keycloakF2Function { cmd, keycloakClient ->
        try {
            keycloakClient.client(cmd.id).remove()
            ClientDeletedEvent(cmd.id)
        } catch (e: Exception) {
            throw I2ApiError(
                description = "Realm[${cmd.realmId}] Client[${cmd.id}] Error deleting",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
