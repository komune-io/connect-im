package city.smartb.im.role.api.service

import city.smartb.im.api.auth.ImAuthenticationResolver
import city.smartb.im.role.domain.features.command.KeycloakRoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.KeycloakRoleAddCompositesFunction
import city.smartb.im.role.domain.features.command.KeycloakRoleCreateCommand
import city.smartb.im.role.domain.features.command.KeycloakRoleCreateFunction
import city.smartb.im.role.domain.features.command.KeycloakRoleUpdateCommand
import city.smartb.im.role.domain.features.command.KeycloakRoleUpdateFunction
import city.smartb.im.role.domain.features.command.RoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.RoleAddedCompositesEvent
import city.smartb.im.role.domain.features.command.RoleCreateCommand
import city.smartb.im.role.domain.features.command.RoleCreatedEvent
import city.smartb.im.role.domain.features.command.RoleUpdateCommand
import city.smartb.im.role.domain.features.command.RoleUpdatedEvent
import f2.dsl.fnc.invokeWith
import org.springframework.stereotype.Service

@Service
class RoleAggregateService(
    private val keycloakRoleAddCompositesFunction: KeycloakRoleAddCompositesFunction,
    private val keycloakRoleCreateFunction: KeycloakRoleCreateFunction,
    private val keycloakRoleUpdateFunction: KeycloakRoleUpdateFunction,
    private val authenticationResolver: ImAuthenticationResolver
) {

    suspend fun roleAddComposites(command: RoleAddCompositesCommand): RoleAddedCompositesEvent {
        val roleName = command.toKeycloakRoleAddCompositesCommand().invokeWith(keycloakRoleAddCompositesFunction).id
        return RoleAddedCompositesEvent(roleName)
    }

    suspend fun roleCreate(command: RoleCreateCommand): RoleCreatedEvent {
        val roleId = command.toKeycloakRoleCreateCommand().invokeWith(keycloakRoleCreateFunction).id
        return RoleCreatedEvent(roleId)
    }

    suspend fun roleUpdate(command: RoleUpdateCommand): RoleUpdatedEvent {
        val roleId = command.toKeycloakRoleUpdateCommand().invokeWith(keycloakRoleUpdateFunction).id
        return RoleUpdatedEvent(roleId)
    }

    private suspend fun RoleUpdateCommand.toKeycloakRoleUpdateCommand(): KeycloakRoleUpdateCommand {
        val auth = authenticationResolver.getAuth()
        return KeycloakRoleUpdateCommand(
            name = this.name,
            composites = this.composites,
            description = this.description,
            isClientRole = this.isClientRole,
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun RoleCreateCommand.toKeycloakRoleCreateCommand(): KeycloakRoleCreateCommand {
        val auth = authenticationResolver.getAuth()
        return KeycloakRoleCreateCommand(
            name = this.name,
            composites = this.composites,
            description = this.description,
            isClientRole = this.isClientRole,
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun RoleAddCompositesCommand.toKeycloakRoleAddCompositesCommand(): KeycloakRoleAddCompositesCommand {
        val auth = authenticationResolver.getAuth()
        return KeycloakRoleAddCompositesCommand(
            roleName = this.roleName,
            composites = this.composites,
            realmId = auth.realmId,
            auth = auth
        )
    }
}
