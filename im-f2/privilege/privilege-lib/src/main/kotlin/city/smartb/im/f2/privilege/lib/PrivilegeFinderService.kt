package city.smartb.im.f2.privilege.lib

import city.smartb.im.commons.SimpleCache
import city.smartb.im.commons.model.RealmId
import city.smartb.im.core.privilege.api.PrivilegeCoreFinderService
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.core.privilege.domain.model.RoleTarget
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
        return privilegeCoreFinderService.getPrivilegeOrNull(realmId, identifier)
            ?.toDTOCached(realmId)
    }

    suspend fun getPrivilege(realmId: RealmId?, identifier: PrivilegeIdentifier): PrivilegeDTO {
        return privilegeCoreFinderService.getPrivilege(realmId, identifier)
            .toDTOCached(realmId)
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

    suspend fun listRoles(
        realmId: RealmId?,
        targets: Collection<RoleTarget>? = null
    ): List<RoleDTOBase> {
        val cache = Cache(realmId)
        return privilegeCoreFinderService.list(
            realmId = realmId,
            types = listOf(PrivilegeType.ROLE),
            targets = targets
        ).onEach { role ->
            cache.roles.register(role.identifier, role as Role)
        }.map { it.toDTOCached(cache) as RoleDTOBase }
    }

    suspend fun listPermissions(realmId: RealmId?): List<PermissionDTOBase> {
        val cache = Cache(realmId)
        return privilegeCoreFinderService.list(
            realmId = realmId,
            types = listOf(PrivilegeType.PERMISSION)
        ).map { it.toDTOCached(cache) as PermissionDTOBase }
    }

    private suspend fun Privilege.toDTOCached(realmId: RealmId?) = toDTOCached(Cache(realmId))
    private suspend fun Privilege.toDTOCached(cache: Cache): PrivilegeDTO = toDTO(
        getRole = cache.roleDTOs::get
    )

    private inner class Cache(realmId: RealmId?) {
        val roles = SimpleCache<RoleIdentifier, Role> { privilegeCoreFinderService.getPrivilege(realmId, it) as Role }
        val roleDTOs = SimpleCache<RoleIdentifier, RoleDTOBase> { roleIdentifier ->
            roles.get(roleIdentifier).toDTOCached(this) as RoleDTOBase
        }
    }
}
