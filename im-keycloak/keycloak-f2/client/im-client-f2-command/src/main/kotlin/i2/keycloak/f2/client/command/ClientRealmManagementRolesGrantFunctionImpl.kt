package i2.keycloak.f2.client.command

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantFunction
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantedEvent
import i2.keycloak.f2.commons.app.asI2Exception
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientRealmManagementRolesGrantFunctionImpl {

    private val logger = LoggerFactory.getLogger(ClientRealmManagementRolesGrantFunctionImpl::class.java)

    @Bean
    fun clientRealmManagementRolesGrantFunction(): ClientRealmManagementRolesGrantFunction = f2Function { cmd ->
        try {
            logger.info("Realm[${cmd.realmId}] Client[${cmd.id}] Granting roles[${cmd.roles}]")
            val realmClient = AuthRealmClientBuilder().build(cmd.auth)

            val targetClientKeycloakId = realmClient.clients(cmd.realmId).findByClientId(cmd.id).first().id
            val targetClientResource = realmClient.getClientResource(cmd.realmId, targetClientKeycloakId)

            val roleProviderClientKeycloakId = realmClient.clients(cmd.realmId).findByClientId("realm-management").first().id

            val rolesToAdd = cmd.roles.map { role ->
                realmClient.getClientResource(cmd.realmId, roleProviderClientKeycloakId).roles().get(role).toRepresentation()
            }
            realmClient
                .getUserResource(cmd.realmId, targetClientResource.serviceAccountUser.id)
                .roles()
                .clientLevel(roleProviderClientKeycloakId)
                .add(rolesToAdd)

            ClientRealmManagementRolesGrantedEvent(cmd.id)
        } catch (e: Exception) {
            throw e.asI2Exception( "Realm[${cmd.realmId}] Client[${cmd.id}] Error granting roles")
        }
    }
}
