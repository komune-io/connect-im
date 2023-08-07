package i2.keycloak.f2.client.command

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.client.domain.features.command.ClientGenerateSecretFunction
import i2.keycloak.f2.client.domain.features.command.ClientGeneratedSecretEvent
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientGenerateSecretFunctionImpl {

    @Bean
    fun clientGenerateSecretFunction(): ClientGenerateSecretFunction = f2Function { cmd ->
        try {
            val realmClient = AuthRealmClientBuilder().build(cmd.auth)
            val newSecret = realmClient.getClientResource(cmd.realmId, cmd.id)
                    .generateNewSecret()

            ClientGeneratedSecretEvent(newSecret.value)
        } catch (e: Exception) {
            throw I2ApiError(
                description = "Realm[${cmd.realmId}] Client[${cmd.id}] Error generating secret",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
