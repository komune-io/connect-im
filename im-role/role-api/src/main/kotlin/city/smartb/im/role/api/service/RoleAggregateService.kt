package city.smartb.im.role.api.service

import city.smartb.im.api.config.ImKeycloakConfig
import city.smartb.im.role.domain.features.command.KeycloakRoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.KeycloakRoleAddCompositesFunction
import city.smartb.im.role.domain.features.command.KeycloakRoleCreateCommand
import city.smartb.im.role.domain.features.command.KeycloakRoleCreateFunction
import city.smartb.im.role.domain.features.command.KeycloakRoleUpdateCommand
import city.smartb.im.role.domain.features.command.KeycloakRoleUpdateFunction
import city.smartb.im.role.domain.features.command.RoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.RoleAddCompositesResult
import city.smartb.im.role.domain.features.command.RoleCreateCommand
import city.smartb.im.role.domain.features.command.RoleCreateResult
import city.smartb.im.role.domain.features.command.RoleUpdateCommand
import city.smartb.im.role.domain.features.command.RoleUpdateResult
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invokeWith
import org.springframework.stereotype.Service

@Service
class RoleAggregateService(
    private val imKeycloakConfig: ImKeycloakConfig,
    private val keycloakRoleAddCompositesFunction: KeycloakRoleAddCompositesFunction,
    private val keycloakRoleCreateFunction: KeycloakRoleCreateFunction,
    private val keycloakRoleUpdateFunction: KeycloakRoleUpdateFunction
) {

    suspend fun roleAddComposites(command: RoleAddCompositesCommand): RoleAddCompositesResult {
        val roleName = command.toKeycloakRoleAddCompositesCommand().invokeWith(keycloakRoleAddCompositesFunction).id
        return RoleAddCompositesResult(roleName)
    }

    suspend fun roleCreate(command: RoleCreateCommand): RoleCreateResult {
        val roleId = command.toKeycloakRoleCreateCommand().invokeWith(keycloakRoleCreateFunction).id
        return RoleCreateResult(roleId)
    }

    suspend fun roleUpdate(command: RoleUpdateCommand): RoleUpdateResult {
        val roleId = command.toKeycloakRoleUpdateCommand().invokeWith(keycloakRoleUpdateFunction).id
        return RoleUpdateResult(roleId)
    }

    private fun RoleUpdateCommand.toKeycloakRoleUpdateCommand() = KeycloakRoleUpdateCommand(
        name = this.name,
        composites = this.composites,
        description = this.description,
        isClientRole = this.isClientRole,
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )

    private fun RoleCreateCommand.toKeycloakRoleCreateCommand() = KeycloakRoleCreateCommand(
        name = this.name,
        composites = this.composites,
        description = this.description,
        isClientRole = this.isClientRole,
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )

    private fun RoleAddCompositesCommand.toKeycloakRoleAddCompositesCommand() = KeycloakRoleAddCompositesCommand(
        roleName = this.roleName,
        composites = this.composites,
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )
}
