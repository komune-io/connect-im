package i2.keycloak.f2.role.query

import org.keycloak.representations.idm.RoleRepresentation

fun List<RoleRepresentation>.asModels() = map(RoleRepresentation::asModel)

fun RoleRepresentation.asModel() = RoleModel(
    id = id,
    name = name,
    description = description,
    isClientRole = clientRole
)
