package city.smartb.im.privilege.lib

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.privilege.domain.permission.command.PermissionDefineCommand
import city.smartb.im.privilege.domain.permission.command.PermissionDefinedEvent
import city.smartb.im.privilege.domain.permission.model.Permission
import city.smartb.im.privilege.domain.role.command.RoleDefineCommand
import city.smartb.im.privilege.domain.role.command.RoleDefinedEvent
import city.smartb.im.privilege.domain.role.model.Role
import city.smartb.im.privilege.lib.model.toRoleRepresentation
import org.springframework.stereotype.Service

@Service
class PrivilegeAggregateService(
    private val privilegeFinderService: PrivilegeFinderService,
    private val keycloakClientProvider: KeycloakClientProvider
) {

    suspend fun define(command: RoleDefineCommand): RoleDefinedEvent {
        val client = keycloakClientProvider.getFor(command)

        val existingRole = privilegeFinderService.getRoleOrNull(command.realmId, command.identifier)

        val newRole = Role(
            id = existingRole?.id.orEmpty(),
            identifier = command.identifier,
            description = command.description,
            targets = command.targets,
            locale = command.locale,
            bindings = command.bindings.orEmpty(),
            permissions = command.permissions.orEmpty()
        )

        if (existingRole == null) {
            client.createRole(newRole)
        } else {
            client.updateRole(existingRole, newRole)
        }

        return RoleDefinedEvent(command.identifier)
    }

    suspend fun define(command: PermissionDefineCommand): PermissionDefinedEvent {
        val client = keycloakClientProvider.getFor(command)

        val existingPermission = privilegeFinderService.getPermissionOrNull(command.realmId, command.identifier)

        val newPermission = Permission(
            id = existingPermission?.id.orEmpty(),
            identifier = command.identifier,
            description = command.description,
        )

        if (existingPermission == null) {
            client.roles().create(newPermission.toRoleRepresentation())
        } else {
            client.role(newPermission.identifier).update(newPermission.toRoleRepresentation())
        }

        return PermissionDefinedEvent(command.identifier)
    }

    private suspend fun KeycloakClient.createRole(role: Role) {
        val permissions = role.permissions.map { permission ->
            privilegeFinderService.getPrivilege(realmId, permission).toRoleRepresentation()
        }

        roles().create(role.toRoleRepresentation())
        if (permissions.isNotEmpty()) {
            role(role.identifier).addComposites(permissions)
        }
    }

    private suspend fun KeycloakClient.updateRole(oldRole: Role, newRole: Role) {
        val newPermissions = newRole.permissions.filter { it !in oldRole.permissions }
            .map { privilegeFinderService.getPrivilege(realmId, it).toRoleRepresentation() }

        val removedPermissions = oldRole.permissions.filter { it !in newRole.permissions }
            .map { privilegeFinderService.getPrivilege(realmId, it).toRoleRepresentation() }


        role(newRole.identifier).update(newRole.toRoleRepresentation())

        if (newPermissions.isNotEmpty()) {
            role(newRole.identifier).addComposites(newPermissions)
        }

        if (removedPermissions.isNotEmpty()) {
            role(newRole.identifier).deleteComposites(removedPermissions)
        }
    }
}
