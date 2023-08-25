package city.smartb.im.f2.privilege.lib

import city.smartb.im.commons.model.RealmId
import city.smartb.im.core.privilege.api.PrivilegeCoreFinderService
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.f2.privilege.lib.model.toDTO
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class PrivilegeFinderService(
    private val privilegeCoreFinderService: PrivilegeCoreFinderService
) {
    suspend fun getPrivilegeOrNull(realmId: RealmId?, identifier: PrivilegeIdentifier): PrivilegeDTO? {
        return privilegeCoreFinderService.getPrivilegeOrNull(realmId, identifier)?.toDTO()
    }

    suspend fun getPrivilege(realmId: RealmId?, identifier: PrivilegeIdentifier): PrivilegeDTO {
        return privilegeCoreFinderService.getPrivilege(realmId, identifier).toDTO()
    }

    suspend fun getRoleOrNull(realmId: RealmId?, identifier: RoleIdentifier): RoleDTOBase? {
        return getPrivilegeOrNull(realmId, identifier)
            ?.takeIf { it is RoleDTOBase } as RoleDTOBase?
    }

    suspend fun getRole(realmId: RealmId?, identifier: RoleIdentifier): RoleDTOBase {
        return getRoleOrNull(realmId, identifier) ?: throw NotFoundException("Role", identifier)
    }

    suspend fun getPermissionOrNull(realmId: RealmId?, identifier: PermissionIdentifier): PermissionDTOBase? {
        return getPrivilegeOrNull(realmId, identifier)
            ?.takeIf { it is PermissionDTOBase } as PermissionDTOBase?
    }

    suspend fun getPermission(realmId: RealmId?, identifier: PermissionIdentifier): PermissionDTOBase {
        return getPermissionOrNull(realmId, identifier) ?: throw NotFoundException("Permission", identifier)
    }
}
