package city.smartb.im.core.privilege.api.model

import city.smartb.im.core.privilege.domain.command.PermissionDefineCommand
import city.smartb.im.core.privilege.domain.command.PrivilegeDefineCommand
import city.smartb.im.core.privilege.domain.command.RoleDefineCommand
import city.smartb.im.core.privilege.domain.model.Permission
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.PrivilegeId
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.Role
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toPrivilege(): Privilege = when (attributes[Privilege::type.name]?.firstOrNull()) {
    PrivilegeType.ROLE.name -> toRole()
    PrivilegeType.PERMISSION.name -> toPermission()
    else -> toPermission()
}

fun Privilege.toRoleRepresentation(): RoleRepresentation = when (this) {
    is Permission -> toRoleRepresentation()
    is Role -> toRoleRepresentation()
}

fun PrivilegeDefineCommand.toPrivilege(id: PrivilegeId?): Privilege = when (this) {
    is PermissionDefineCommand -> toPermission(id)
    is RoleDefineCommand -> toRole(id)
}
