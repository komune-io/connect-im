package city.smartb.im.core.privilege.api.model

import city.smartb.im.core.privilege.domain.command.PermissionDefineCommand
import city.smartb.im.core.privilege.domain.model.Permission
import city.smartb.im.core.privilege.domain.model.PermissionId
import city.smartb.im.core.privilege.domain.model.Privilege
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toPermission() = Permission(
    id = id,
    identifier = name,
    description = description.orEmpty(),
)

fun Permission.toRoleRepresentation() = RoleRepresentation().also {
    it.id = id.ifEmpty { null }
    it.name = identifier
    it.description = description
    it.clientRole = false
    it.attributes = mapOf(
        Privilege::type.name to listOf(type.name),
    )
}

fun PermissionDefineCommand.toPermission(id: PermissionId?) = Permission(
    id = id.orEmpty(),
    identifier = identifier,
    description = description,
)
