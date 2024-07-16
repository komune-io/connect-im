package io.komune.im.core.privilege.api.model

import io.komune.im.commons.model.FeatureId
import io.komune.im.core.privilege.domain.command.FeatureCoreDefineCommand
import io.komune.im.core.privilege.domain.model.FeatureModel
import io.komune.im.core.privilege.domain.model.Privilege
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.toFeature() = FeatureModel(
    id = id,
    identifier = name,
    description = description.orEmpty()
)

fun FeatureModel.toRoleRepresentation() = RoleRepresentation().also {
    it.id = id.ifEmpty { null }
    it.name = identifier
    it.description = description
    it.clientRole = false
    it.attributes = mapOf(
        Privilege::type.name to listOf(type.name)
    )
}

fun FeatureCoreDefineCommand.toFeature(id: FeatureId?) = FeatureModel(
    id = id.orEmpty(),
    identifier = identifier,
    description = description,
)
