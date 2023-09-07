package i2.keycloak.f2.user.query.model

import city.smartb.im.commons.model.UserId
import i2.keycloak.f2.role.domain.RolesCompositesModel
import i2.keycloak.f2.user.domain.model.UserModel
import org.keycloak.representations.idm.UserRepresentation

suspend fun Iterable<UserRepresentation>.asModels(getRealmRoles: suspend (UserId) -> RolesCompositesModel): List<UserModel>
		= map { user -> user.asModel(getRealmRoles) }

suspend fun UserRepresentation.asModel(getRealmRoles: suspend (UserId) -> RolesCompositesModel): UserModel {
	return UserModel(
		id = id,
		email = email,
		firstName = firstName,
		lastName = lastName,
		roles = getRealmRoles(id),
		attributes = attributes.orEmpty().mapValues { (_, value) -> value.first() },
		enabled = isEnabled,
		creationDate = createdTimestamp
	)
}
