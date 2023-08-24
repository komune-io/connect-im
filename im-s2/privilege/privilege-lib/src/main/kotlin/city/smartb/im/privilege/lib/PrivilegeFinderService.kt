package city.smartb.im.privilege.lib

import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.privilege.domain.model.Privilege
import city.smartb.im.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.privilege.domain.permission.model.Permission
import city.smartb.im.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.privilege.domain.role.model.Role
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.privilege.lib.model.toPrivilege
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class PrivilegeFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
) {
    suspend fun getPrivilegeOrNull(realmId: RealmId?, identifier: PrivilegeIdentifier): Privilege? {
        val client = keycloakClientProvider.getFor(realmId)

        return try {
            client.role(identifier)
                .toRepresentation()
                .toPrivilege()
        } catch (e: javax.ws.rs.NotFoundException) {
            null
        }
    }

    suspend fun getPrivilege(realmId: RealmId?, identifier: PrivilegeIdentifier): Privilege {
        return getPrivilegeOrNull(realmId, identifier) ?: throw NotFoundException("Privilege", identifier)
    }

    suspend fun getRoleOrNull(realmId: RealmId?, identifier: RoleIdentifier): Role? {
        return getPrivilegeOrNull(realmId, identifier)
            ?.takeIf { it is Role } as Role?
    }

    suspend fun getRole(realmId: RealmId?, identifier: RoleIdentifier): Role {
        return getRoleOrNull(realmId, identifier) ?: throw NotFoundException("Role", identifier)
    }

    suspend fun getPermissionOrNull(realmId: RealmId?, identifier: PermissionIdentifier): Permission? {
        return getPrivilegeOrNull(realmId, identifier)
            ?.takeIf { it is Permission } as Permission?
    }

    suspend fun getPermission(realmId: RealmId?, identifier: PermissionIdentifier): Permission {
        return getPermissionOrNull(realmId, identifier) ?: throw NotFoundException("Permission", identifier)
    }
}
