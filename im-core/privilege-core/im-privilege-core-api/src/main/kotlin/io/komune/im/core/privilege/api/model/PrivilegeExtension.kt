package io.komune.im.core.privilege.api.model

import io.komune.im.commons.model.PrivilegeId
import io.komune.im.core.privilege.api.exception.PrivilegeWrongTargetException
import io.komune.im.core.privilege.domain.command.PermissionCoreDefineCommand
import io.komune.im.core.privilege.domain.command.PrivilegeCoreDefineCommand
import io.komune.im.core.privilege.domain.command.RoleCoreDefineCommand
import io.komune.im.core.privilege.domain.model.PermissionModel
import io.komune.im.core.privilege.domain.model.Privilege
import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.core.privilege.domain.model.RoleModel
import io.komune.im.core.privilege.domain.model.RoleTarget
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toPrivilege(): Privilege = when (attributes[Privilege::type.name]?.firstOrNull()) {
    PrivilegeType.ROLE.name -> toRole()
    PrivilegeType.PERMISSION.name -> toPermission()
    else -> toPermission()
}

fun Privilege.toRoleRepresentation(): RoleRepresentation = when (this) {
    is PermissionModel -> toRoleRepresentation()
    is RoleModel -> toRoleRepresentation()
}

fun PrivilegeCoreDefineCommand.toPrivilege(id: PrivilegeId?): Privilege = when (this) {
    is PermissionCoreDefineCommand -> toPermission(id)
    is RoleCoreDefineCommand -> toRole(id)
}

fun Privilege.checkTarget(target: RoleTarget) {
    if (this !is RoleModel || (targets.isNotEmpty() && target !in targets)) {
        throw PrivilegeWrongTargetException(identifier, target)
    }
}
