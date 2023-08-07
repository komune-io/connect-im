package i2.keycloak.f2.group.query.model

import i2.keycloak.f2.group.domain.model.GroupModel
import i2.keycloak.f2.role.domain.RolesCompositesModel
import org.keycloak.representations.idm.GroupRepresentation

val i2GroupAttributes = setOf(
    GroupModel::enabled.name
)

fun GroupRepresentation.asModel(getComposites: (roleName: String) -> List<String>): GroupModel {
    val parsedAttributes = attributes.parseAttributes()
    val composites = realmRoles.orEmpty().flatMap{ getComposites(it) + it }.distinct()
    return GroupModel(
        id = id,
        name = name,
        attributes = parsedAttributes.filterKeys { it !in i2GroupAttributes },
        roles = RolesCompositesModel(
            assignedRoles = realmRoles.orEmpty(),
            effectiveRoles = composites
        ),
        enabled = parsedAttributes.isEnable()
    )
}

fun Map<String, String>.isEnable(): Boolean {
    return get(GroupModel::enabled.name)?.toBoolean() ?: true
}
fun Map<String, List<String>>.parseAttributes(): Map<String, String> {
    return orEmpty()
        .mapValues { (_, value) -> value.first() }
}
