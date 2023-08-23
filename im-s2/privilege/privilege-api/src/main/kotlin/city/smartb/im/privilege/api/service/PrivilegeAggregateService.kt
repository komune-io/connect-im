package city.smartb.im.privilege.api.service

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.privilege.api.model.toRoleRepresentation
import city.smartb.im.privilege.domain.role.command.RoleDefineCommand
import city.smartb.im.privilege.domain.role.command.RoleDefinedEvent
import city.smartb.im.privilege.domain.role.model.Role
import org.springframework.stereotype.Service

@Service
class PrivilegeAggregateService(
    private val privilegeFinderService: PrivilegeFinderService,
    private val keycloakClientProvider: KeycloakClientProvider
) {

    suspend fun define(command: RoleDefineCommand): RoleDefinedEvent {
        val client = keycloakClientProvider.get()

        val newRole = Role(
            identifier = command.identifier,
            description = command.description,
            targets = command.targets,
            locale = command.locale,
            bindings = command.bindings.orEmpty(),
            permissions = command.permissions.orEmpty()
        )

        val existingRole = privilegeFinderService.getRoleOrNull(command.identifier)
        if (existingRole == null) {
            client.createRole(newRole)
        } else {
            client.updateRole(existingRole, newRole)
        }

        return RoleDefinedEvent(command.identifier)
    }

    private suspend fun KeycloakClient.createRole(role: Role) {
        val permissions = role.permissions.map { permission ->
            privilegeFinderService.getPrivilege(permission).toRoleRepresentation()
        }

        roles().create(role.toRoleRepresentation())
        if (permissions.isNotEmpty()) {
            getRoleResource(role.identifier).addComposites(permissions)
        }
    }

    private suspend fun KeycloakClient.updateRole(oldRole: Role, newRole: Role) {
        val newPermissions = newRole.permissions.filter { it !in oldRole.permissions }
            .map { privilegeFinderService.getPrivilege(it).toRoleRepresentation() }

        val removedPermissions = oldRole.permissions.filter { it !in newRole.permissions }
            .map { privilegeFinderService.getPrivilege(it).toRoleRepresentation() }


        getRoleResource(newRole.identifier).update(newRole.toRoleRepresentation())

        if (newPermissions.isNotEmpty()) {
            getRoleResource(newRole.identifier).addComposites(newPermissions)
        }

        if (removedPermissions.isNotEmpty()) {
            getRoleResource(newRole.identifier).deleteComposites(removedPermissions)
        }
    }
}
