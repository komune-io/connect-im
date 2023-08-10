package i2.keycloak.f2.group.domain.model

import i2.keycloak.f2.role.domain.RolesCompositesModel

typealias GroupId = String

data class GroupModel(
    val id: GroupId,
    val name: String,
    val attributes: Map<String, String>,
    val roles: RolesCompositesModel,
    val enabled: Boolean
)

val HiddenGroupAttributes = setOf(
    GroupModel::enabled.name
)
