package city.smartb.im.user.api.service

import city.smartb.im.api.auth.ImAuthenticationResolver
import city.smartb.im.commons.model.AddressBase
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.organization.api.model.orEmpty
import city.smartb.im.organization.domain.model.OrganizationRef
import city.smartb.im.user.domain.model.UserBase
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsFunction
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQuery
import i2.keycloak.f2.user.domain.model.UserModel

class UserTransformer(
    private val authenticationResolver: ImAuthenticationResolver,
    private val userGetGroupsQueryFunction: UserGetGroupsFunction
) {
	suspend fun toUser(user: UserModel): UserBase {
		val auth = authenticationResolver.getAuth()
		val group = UserGetGroupsQuery(
			userId = user.id,
			realmId =  auth.realmId,
			auth = auth
		).invokeWith(userGetGroupsQueryFunction).items.firstOrNull()?.let { group ->
			OrganizationRef(
				id = group.id,
				name = group.name,
				roles = group.roles
			)
		}

		return UserBase(
			id = user.id,
			memberOf = group,
			email = user.email ?: "",
			givenName = user.firstName ?: "",
			familyName = user.lastName ?: "",
			address = user.attributes[UserBase::address.name]?.first()?.parseJsonTo(AddressBase::class.java).orEmpty(),
			phone = user.attributes[UserBase::phone.name]?.firstOrNull(),
			roles = user.roles,
			sendEmailLink = user.attributes[UserBase::sendEmailLink.name]?.firstOrNull().toBoolean()
		)
	}
	
}
