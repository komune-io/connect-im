package city.smartb.im.core.privilege.api.model

import city.smartb.im.commons.utils.parseJson
import city.smartb.im.commons.utils.toJson
import city.smartb.im.core.privilege.domain.command.RoleDefineCommand
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.core.privilege.domain.model.RoleId
import city.smartb.im.core.privilege.domain.model.RoleIdentifier
import city.smartb.im.core.privilege.domain.model.RoleTarget
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toRole() = Role(
    id = id,
    identifier = name,
    description = description.orEmpty(),
    targets = attributes[Role::targets.name].orEmpty().map { RoleTarget.valueOf(it) },
    bindings = attributes[Role::bindings.name]?.firstOrNull()
        ?.parseJson<Map<String, List<RoleIdentifier>>>()
        ?.mapKeys { (target) -> RoleTarget.valueOf(target) }
        .orEmpty(),
    locale = attributes[Role::locale.name]?.firstOrNull()?.parseJson<Map<String, String>>().orEmpty(),
    permissions = attributes[Role::permissions.name].orEmpty()
)

fun Role.toRoleRepresentation() = RoleRepresentation().also {
    it.id = id.ifEmpty { null }
    it.name = identifier
    it.description = description
    it.clientRole = false
    it.attributes = mapOf(
        Privilege::type.name to listOf(type.name),
        Role::targets.name to targets.map(RoleTarget::name).distinct(),
        Role::locale.name to listOf(locale.toJson()),
        Role::bindings.name to listOf(bindings.toJson()),
        Role::permissions.name to permissions.distinct()
    )
}

fun RoleDefineCommand.toRole(id: RoleId?) = Role(
    id = id.orEmpty(),
    identifier = identifier,
    description = description,
    targets = targets,
    locale = locale,
    bindings = bindings.orEmpty(),
    permissions = permissions.orEmpty()
)
