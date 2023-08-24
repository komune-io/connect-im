package city.smartb.im.privilege.lib.model

import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.commons.utils.toJson
import city.smartb.im.privilege.domain.model.Privilege
import city.smartb.im.privilege.domain.role.model.Role
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.privilege.domain.role.model.RoleTarget
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toRole() = Role(
    id = id,
    identifier = name,
    description = description,
    targets = attributes[Role::targets.name].orEmpty().map { RoleTarget.valueOf(it) },
    bindings = attributes[Role::bindings.name]?.firstOrNull()?.parseJsonTo<Map<RoleTarget, RoleIdentifier>>().orEmpty(),
    locale = attributes[Role::locale.name]?.firstOrNull()?.parseJsonTo<Map<String, String>>().orEmpty(),
    permissions = attributes[Role::permissions.name].orEmpty()
)

fun Role.toRoleRepresentation() = RoleRepresentation().also {
    it.id = id.ifEmpty { null }
    it.name = identifier
    it.description = description
    it.clientRole = false
    it.attributes = mapOf(
        Privilege::type.name to listOf(type.name),
        Role::targets.name to targets.map(RoleTarget::name),
        Role::locale.name to listOf(locale.toJson()),
        Role::bindings.name to listOf(bindings.toJson()),
        Role::permissions.name to permissions
    )
}
