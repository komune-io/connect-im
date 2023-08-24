package i2.keycloak.f2.client.command

import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantedEvent
import i2.keycloak.f2.commons.app.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientServiceAccountRolesGrantFunctionImpl {

    @Bean
    fun clientServiceAccountRolesGrantFunction(): ClientServiceAccountRolesGrantFunction = keycloakF2Function { cmd, keycloakClient ->
        try {
            val targetClientResource = keycloakClient.client(cmd.id)
            val rolesToAdd = cmd.roles.map { role ->
                keycloakClient.role(role).toRepresentation()
            }
            keycloakClient.user(targetClientResource.serviceAccountUser.id)
                .roles()
                .realmLevel()
                .add(rolesToAdd)

            ClientServiceAccountRolesGrantedEvent(cmd.id)
        } catch (e: Exception) {
            throw e.asI2Exception("Realm[${cmd.realmId}] Client[${cmd.id}] Error granting roles")
        }
    }
}
