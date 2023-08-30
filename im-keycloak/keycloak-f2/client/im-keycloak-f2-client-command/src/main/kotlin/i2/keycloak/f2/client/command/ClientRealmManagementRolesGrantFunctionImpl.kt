package i2.keycloak.f2.client.command

import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantFunction
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantedEvent
import i2.keycloak.f2.commons.app.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientRealmManagementRolesGrantFunctionImpl {

    private val logger = LoggerFactory.getLogger(ClientRealmManagementRolesGrantFunctionImpl::class.java)

    @Bean
    fun clientRealmManagementRolesGrantFunction(): ClientRealmManagementRolesGrantFunction = keycloakF2Function { cmd, keycloakClient ->
        try {
            logger.info("Realm[${cmd.realmId}] Client[${cmd.id}] Granting roles[${cmd.roles}]")
            val targetClientKeycloakId = keycloakClient.getClientByIdentifier(cmd.id)!!.id
            val targetClientResource = keycloakClient.client(targetClientKeycloakId)

            val roleProviderClientKeycloakId = keycloakClient.clients().findByClientId("realm-management").first().id

            val rolesToAdd = cmd.roles.map { role ->
                keycloakClient.client(roleProviderClientKeycloakId).roles().get(role).toRepresentation()
            }
            keycloakClient.user(targetClientResource.serviceAccountUser.id)
                .roles()
                .clientLevel(roleProviderClientKeycloakId)
                .add(rolesToAdd)

            ClientRealmManagementRolesGrantedEvent(cmd.id)
        } catch (e: Exception) {
            throw e.asI2Exception( "Realm[${cmd.realmId}] Client[${cmd.id}] Error granting roles")
        }
    }
}
