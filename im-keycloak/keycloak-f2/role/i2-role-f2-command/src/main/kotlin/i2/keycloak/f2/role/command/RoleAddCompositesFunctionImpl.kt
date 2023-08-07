package i2.keycloak.f2.role.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.role.domain.features.command.RoleAddCompositesFunction
import i2.keycloak.f2.role.domain.features.command.RoleAddedCompositesEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleAddCompositesFunctionImpl {

    @Bean
    fun roleAddCompositesFunction(): RoleAddCompositesFunction = keycloakF2Function { cmd, client ->
        val composites = cmd.composites.map { roleId ->
            client.getRoleResource(cmd.realmId, roleId).toRepresentation()
        }
        client.getRoleResource(cmd.realmId, cmd.roleName).addComposites(composites)

        RoleAddedCompositesEvent(cmd.roleName)
    }
}
