package city.smartb.im.f2.privilege.lib

import city.smartb.im.commons.SimpleCache
import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.core.privilege.api.PrivilegeCoreFinderService
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
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

    suspend fun getRoleOrNull(identifier: RoleIdentifier): RoleDTOBase? {
        return getPrivilegeOrNull(identifier)
            ?.takeIf { it is RoleDTOBase } as RoleDTOBase?
    }

    suspend fun getRole(identifier: RoleIdentifier): RoleDTOBase {
        return getRoleOrNull(identifier) ?: throw NotFoundException("Role", identifier)
    }

    suspend fun getPermissionOrNull(identifier: PermissionIdentifier): PermissionDTOBase? {
        return getPrivilegeOrNull(identifier)
            ?.takeIf { it is PermissionDTOBase } as PermissionDTOBase?
    }

    suspend fun getPermission(identifier: PermissionIdentifier): PermissionDTOBase {
        return getPermissionOrNull(identifier) ?: throw NotFoundException("Permission", identifier)
    }

    suspend fun listRoles(
        targets: Collection<RoleTarget>? = null
    ): List<RoleDTOBase> {
        val cache = Cache()
        return privilegeCoreFinderService.list(
            types = listOf(PrivilegeType.ROLE),
            targets = targets
        ).onEach { role ->
            cache.roles.register(role.identifier, role as Role)
        }.map { it.toDTOCached(cache) as RoleDTOBase }
    }

    suspend fun listPermissions(): List<PermissionDTOBase> {
        val cache = Cache()
        return privilegeCoreFinderService.list(
            types = listOf(PrivilegeType.PERMISSION)
        ).map { it.toDTOCached(cache) as PermissionDTOBase }
    }

    private suspend fun Privilege.toDTOCached(cache: Cache = Cache()): PrivilegeDTO = toDTO(
        getRole = cache.roleDTOs::get
    )

    private inner class Cache {
        val roles = SimpleCache<RoleIdentifier, Role> { privilegeCoreFinderService.getPrivilege(it) as Role }
        val roleDTOs = SimpleCache<RoleIdentifier, RoleDTOBase> { roleIdentifier ->
            roles.get(roleIdentifier).toDTOCached(this) as RoleDTOBase
        }
    }
}
