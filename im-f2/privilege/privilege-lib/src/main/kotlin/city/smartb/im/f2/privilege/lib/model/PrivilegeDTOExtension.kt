package city.smartb.im.f2.privilege.lib.model

import city.smartb.im.core.privilege.domain.model.Permission
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase

fun Privilege.toDTO(): PrivilegeDTO = when (this) {
    is Permission -> toDTO()
    is Role -> toDTO()
}

fun Permission.toDTO() = PermissionDTOBase(
    id = id,
    identifier = identifier,
    description = description,
)

fun Role.toDTO() = RoleDTOBase(
    id = id,
    identifier = identifier,
    description = description,
    targets = targets.map(RoleTarget::name),
    bindings = bindings.mapKeys { (target) -> target.name },
    locale = locale,
    permissions = permissions,
)
