package city.smartb.im.core.organization.api.model

import city.smartb.im.commons.utils.parseJson
import city.smartb.im.core.organization.domain.model.Organization
import org.keycloak.representations.idm.GroupRepresentation

fun GroupRepresentation.toOrganization(): Organization {
    val singleAttributes = attributes
        .mapValues { (_, values) -> values.firstOrNull() }
        .filterValues { !it.isNullOrBlank() } as Map<String, String>

    return Organization(
        id = id,
        identifier = name,
        displayName = singleAttributes[Organization::displayName.name] ?: name,
        description = singleAttributes[Organization::description.name],
        address = singleAttributes[Organization::address.name]?.parseJson(),
        attributes = singleAttributes,
        roles = realmRoles,
        enabled = singleAttributes[Organization::enabled.name]?.toBoolean() ?: true,
        disabledBy = singleAttributes[Organization::disabledBy.name],
        creationDate = singleAttributes[Organization::creationDate.name]?.toLong() ?: 0,
        disabledDate = singleAttributes[Organization::disabledDate.name]?.toLong()
    )
}
