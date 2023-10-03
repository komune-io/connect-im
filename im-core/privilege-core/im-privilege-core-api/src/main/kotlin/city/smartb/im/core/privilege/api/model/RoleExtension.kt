package city.smartb.im.core.privilege.api.model

import city.smartb.im.commons.model.RoleId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.utils.parseJson
import city.smartb.im.commons.utils.toJson
import city.smartb.im.core.privilege.domain.command.RoleCoreDefineCommand
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.RoleModel
import city.smartb.im.core.privilege.domain.model.RoleTarget
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toRole() = RoleModel(
    id = id,
    identifier = name,
    description = description.orEmpty(),
    targets = attributes[RoleModel::targets.name].orEmpty().map { RoleTarget.valueOf(it) },
    bindings = attributes[RoleModel::bindings.name]?.firstOrNull()
        ?.parseJson<Map<String, List<RoleIdentifier>>>()
        ?.mapKeys { (target) -> RoleTarget.valueOf(target) }
        .orEmpty(),
    locale = attributes[RoleModel::locale.name]?.firstOrNull()?.parseJson<Map<String, String>>().orEmpty(),
    permissions = attributes[RoleModel::permissions.name].orEmpty()
)

fun RoleModel.toRoleRepresentation() = RoleRepresentation().also {
    it.id = id.ifEmpty { null }
    it.name = identifier
    it.description = description
    it.clientRole = false
    it.attributes = mapOf(
        Privilege::type.name to listOf(type.name),
        RoleModel::targets.name to targets.map(RoleTarget::name).distinct(),
        RoleModel::locale.name to listOf(locale.toJson()),
        RoleModel::bindings.name to listOf(bindings.toJson()),
        RoleModel::permissions.name to permissions.distinct()
    )
}

fun RoleCoreDefineCommand.toRole(id: RoleId?) = RoleModel(
    id = id.orEmpty(),
    identifier = identifier,
    description = description,
    targets = targets,
    locale = locale,
    bindings = bindings.orEmpty(),
    permissions = permissions.orEmpty()
)
