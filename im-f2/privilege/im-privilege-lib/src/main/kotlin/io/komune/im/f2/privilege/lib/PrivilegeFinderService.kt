package io.komune.im.f2.privilege.lib

import f2.spring.exception.NotFoundException
import io.komune.im.commons.SimpleCache
import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.core.privilege.api.PrivilegeCoreFinderService
import io.komune.im.core.privilege.domain.model.Privilege
import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.core.privilege.domain.model.RoleModel
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.privilege.domain.feature.model.Feature
import io.komune.im.f2.privilege.domain.model.PrivilegeDTO
import io.komune.im.f2.privilege.domain.permission.model.Permission
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.privilege.lib.model.toDTO
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

    suspend fun getFeatureOrNull(identifier: FeatureIdentifier): Feature? {
        return getPrivilegeOrNull(identifier)
            ?.takeIf { it is Feature } as Feature?
    }

    suspend fun getFeature(identifier: FeatureIdentifier): Feature {
        return getFeatureOrNull(identifier) ?: throw NotFoundException("Feature", identifier)
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

    suspend fun listFeatures(): List<Feature> {
        val cache = Cache()
        return privilegeCoreFinderService.list(
            types = listOf(PrivilegeType.FEATURE)
        ).map { it.toDTOCached(cache) as Feature }
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
