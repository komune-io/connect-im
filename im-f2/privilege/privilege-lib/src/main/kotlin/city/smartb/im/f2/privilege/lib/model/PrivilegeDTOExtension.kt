package city.smartb.im.f2.privilege.lib.model

import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.core.privilege.domain.model.Permission
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun Privilege.toDTO(
    getRole: suspend (RoleIdentifier) -> RoleDTOBase
): PrivilegeDTO = when (this) {
    is Permission -> toDTO()
    is Role -> toDTO(getRole)
}

suspend fun Permission.toDTO() = PermissionDTOBase(
    id = id,
    identifier = identifier,
    description = description,
)

suspend fun Role.toDTO(
    getRole: suspend (RoleIdentifier) -> RoleDTOBase
) = coroutineScope {
    RoleDTOBase(
        id = id,
        identifier = identifier,
        description = description,
        targets = targets.map(RoleTarget::name),
        bindings = bindings.mapKeys { (target) -> target.name }
            .mapValues { (_, roles) ->
                roles.map { async {
                    if (it != identifier) { // prevents infinite loop on self-bind
                        getRole(it)
                    } else {
                        null
                    }
                } }.awaitAll().filterNotNull()
            },
        locale = locale,
        permissions = permissions,
    )
}
