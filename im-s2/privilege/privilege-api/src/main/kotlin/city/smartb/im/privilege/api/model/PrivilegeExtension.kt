package city.smartb.im.privilege.api.model

import city.smartb.im.privilege.domain.model.Privilege
import city.smartb.im.privilege.domain.model.PrivilegeType
import city.smartb.im.privilege.domain.permission.model.Permission
import city.smartb.im.privilege.domain.role.model.Role
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toPrivilege(): Privilege? = when (attributes[Privilege::type.name]?.firstOrNull()) {
    PrivilegeType.PERMISSION.name -> toPermission()
    PrivilegeType.ROLE.name -> toRole()
    else -> null
}

fun Privilege.toRoleRepresentation(): RoleRepresentation = when (this) {
    is Permission -> toRoleRepresentation()
    is Role -> toRoleRepresentation()
    else -> throw NotImplementedError()
}
