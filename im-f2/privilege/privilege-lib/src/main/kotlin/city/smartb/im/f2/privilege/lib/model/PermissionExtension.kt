package city.smartb.im.f2.privilege.lib.model

import city.smartb.im.f2.privilege.domain.permission.model.Permission
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
        city.smartb.im.f2.privilege.domain.model.Privilege::type.name to listOf(type.name),
    )
}
