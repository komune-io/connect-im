package io.komune.im.core.privilege.api.model

import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.commons.model.PermissionId
import io.komune.im.commons.utils.parseJsonTo
import io.komune.im.commons.utils.toJson
import io.komune.im.core.privilege.domain.command.PermissionCoreDefineCommand
import io.komune.im.core.privilege.domain.model.PermissionModel
import io.komune.im.core.privilege.domain.model.Privilege
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toPermission() = PermissionModel(
    id = id,
    identifier = name,
    description = description.orEmpty(),
    features = attributes[PermissionModel::features.name]?.map { it.parseJsonTo(Array<FeatureIdentifier>::class.java) }
)

fun PermissionModel.toRoleRepresentation() = RoleRepresentation().also {
    it.id = id.ifEmpty { null }
    it.name = identifier
    it.description = description
    it.clientRole = false
    it.attributes = mapOf(
        Privilege::type.name to listOf(type.name),
        PermissionModel::features.name to features?.map(List<FeatureIdentifier>::toJson),
    )
}

fun PermissionCoreDefineCommand.toPermission(id: PermissionId?) = PermissionModel(
    id = id.orEmpty(),
    identifier = identifier,
    description = description,
    features = features,
)
