package city.smartb.im.f2.privilege.lib.model

import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.core.privilege.domain.model.PermissionModel
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.RoleModel
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.domain.permission.model.Permission
import city.smartb.im.f2.privilege.domain.role.model.Role
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun Privilege.toDTO(
    getRole: suspend (RoleIdentifier) -> Role
): PrivilegeDTO = when (this) {
    is PermissionModel -> toDTO()
    is RoleModel -> toDTO(getRole)
}

suspend fun PermissionModel.toDTO() = Permission(
    id = id,
    identifier = identifier,
    description = description,
)

suspend fun RoleModel.toDTO(
    getRole: suspend (RoleIdentifier) -> Role
) = coroutineScope {
    Role(
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
