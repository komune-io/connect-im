package city.smartb.im.privilege.lib.model

import city.smartb.im.privilege.domain.model.Privilege
import city.smartb.im.privilege.domain.permission.model.Permission
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toPermission() = Permission(
    id = id,
    identifier = name,
    description = description,
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
