package city.smartb.im.f2.privilege.lib

import city.smartb.im.commons.SimpleCache
import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.core.privilege.api.PrivilegeCoreFinderService
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.RoleModel
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.domain.permission.model.Permission
import city.smartb.im.f2.privilege.domain.role.model.Role
import city.smartb.im.f2.privilege.lib.model.toDTO
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class PrivilegeFinderService(
    private val privilegeCoreFinderService: PrivilegeCoreFinderService
) {
    suspend fun getPrivilegeOrNull(identifier: PrivilegeIdentifier): PrivilegeDTO? {
        return privilegeCoreFinderService.getPrivilegeOrNull(identifier)
            ?.toDTOCached()
    }

    suspend fun getPrivilege(identifier: PrivilegeIdentifier): PrivilegeDTO {
        return privilegeCoreFinderService.getPrivilege(identifier)
            .toDTOCached()
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

    suspend fun listRoles(
        targets: Collection<RoleTarget>? = null
    ): List<Role> {
        val cache = Cache()
        return privilegeCoreFinderService.list(
            types = listOf(PrivilegeType.ROLE),
            targets = targets
        ).onEach { role ->
            cache.roles.register(role.identifier, role as RoleModel)
        }.map { it.toDTOCached(cache) as Role }
    }

    suspend fun listPermissions(): List<Permission> {
        val cache = Cache()
        return privilegeCoreFinderService.list(
            types = listOf(PrivilegeType.PERMISSION)
        ).map { it.toDTOCached(cache) as Permission }
    }

    private suspend fun Privilege.toDTOCached(cache: Cache = Cache()): PrivilegeDTO = toDTO(
        getRole = cache.roleDTOs::get
    )

    private inner class Cache {
        val roles = SimpleCache<RoleIdentifier, RoleModel> { privilegeCoreFinderService.getPrivilege(it) as RoleModel }
        val roleDTOs = SimpleCache<RoleIdentifier, Role> { roleIdentifier ->
            roles.get(roleIdentifier).toDTOCached(this) as Role
        }
    }
}
