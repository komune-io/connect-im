package city.smartb.im.privilege.api.service

import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.privilege.api.model.toPrivilege
import city.smartb.im.privilege.domain.model.Privilege
import city.smartb.im.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.privilege.domain.permission.model.Permission
import city.smartb.im.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.privilege.domain.role.model.Role
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class PrivilegeFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
) {
    suspend fun getPrivilegeOrNull(identifier: PrivilegeIdentifier): Privilege? {
        val client = keycloakClientProvider.get()

        return try {
            client.getRoleResource(identifier)
                .toRepresentation()
                .toPrivilege()
        } catch (e: javax.ws.rs.NotFoundException) {
            null
        }
    }

    suspend fun getPrivilege(identifier: PrivilegeIdentifier): Privilege {
        return getPrivilegeOrNull(identifier) ?: throw NotFoundException("Privilege", identifier)
    }

    suspend fun getRoleOrNull(identifier: RoleIdentifier): Role? {
        return getPrivilegeOrNull(identifier)
            ?.takeIf { it is Role } as Role?
    }

    suspend fun getRole(identifier: RoleIdentifier): Role {
        return getRoleOrNull(identifier) ?: throw NotFoundException("Role", identifier)
    }

    suspend fun getPermissionOrNull(identifier: PermissionIdentifier): Permission? {
        return getPrivilegeOrNull(identifier)
            ?.takeIf { it is Permission } as Permission?
    }

    suspend fun getPermission(identifier: PermissionIdentifier): Permission {
        return getPermissionOrNull(identifier) ?: throw NotFoundException("Permission", identifier)
    }
}
