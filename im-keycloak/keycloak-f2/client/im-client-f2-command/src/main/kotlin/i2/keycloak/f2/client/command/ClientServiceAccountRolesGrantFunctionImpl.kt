package i2.keycloak.f2.client.command

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantedEvent
import i2.keycloak.f2.commons.app.asI2Exception
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientServiceAccountRolesGrantFunctionImpl {

    @Bean
    fun clientServiceAccountRolesGrantFunction(): ClientServiceAccountRolesGrantFunction = f2Function { cmd ->
        try {
            val realmClient = AuthRealmClientBuilder().build(cmd.auth)

            val targetClientResource = realmClient.getClientResource(cmd.realmId, cmd.id)
            val rolesToAdd = cmd.roles.map { role ->
                realmClient.roles().get(role).toRepresentation()
            }
            realmClient
                .getUserResource(cmd.realmId, targetClientResource.serviceAccountUser.id)
                .roles()
                .realmLevel()
                .add(rolesToAdd)

            ClientServiceAccountRolesGrantedEvent(cmd.id)
        } catch (e: Exception) {
            throw e.asI2Exception("Realm[${cmd.realmId}] Client[${cmd.id}] Error granting roles")
        }
    }
}
