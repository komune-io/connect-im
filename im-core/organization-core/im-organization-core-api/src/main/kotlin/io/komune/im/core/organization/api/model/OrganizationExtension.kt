package io.komune.im.core.organization.api.model

import io.komune.im.commons.utils.parseJson
import io.komune.im.core.organization.domain.model.OrganizationModel
import org.keycloak.representations.idm.GroupRepresentation

fun GroupRepresentation.toOrganization(): OrganizationModel {
    val singleAttributes = attributes
        .mapNotNull { (key, values) -> values.firstOrNull()?.takeIf { it.isNotBlank() }?.let { key to it } }
        .toMap()

    return OrganizationModel(
        id = id,
        identifier = name,
        displayName = singleAttributes[OrganizationModel::displayName.name] ?: name,
        description = singleAttributes[OrganizationModel::description.name],
        address = singleAttributes[OrganizationModel::address.name]?.parseJson(),
        attributes = singleAttributes,
        roles = realmRoles,
        enabled = singleAttributes[OrganizationModel::enabled.name]?.toBoolean() ?: true,
        disabledBy = singleAttributes[OrganizationModel::disabledBy.name],
        creationDate = singleAttributes[OrganizationModel::creationDate.name]?.toLong() ?: 0,
        disabledDate = singleAttributes[OrganizationModel::disabledDate.name]?.toLong()
    )
}
