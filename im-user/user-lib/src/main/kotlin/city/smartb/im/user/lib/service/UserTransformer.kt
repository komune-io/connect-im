package city.smartb.im.user.lib.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.orEmpty
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.organization.domain.model.OrganizationRef
import city.smartb.im.user.domain.model.User
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsFunction
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQuery
import i2.keycloak.f2.user.domain.model.UserModel
import org.springframework.stereotype.Service

@Service
class UserTransformer(
	private val authenticationResolver: ImAuthenticationProvider,
	private val userGetGroupsQueryFunction: UserGetGroupsFunction
) {
	suspend fun toUser(user: UserModel): User {
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

		val imAttributes = listOf(
			User::address.name,
			User::disabledBy.name,
			User::disabledDate.name,
			User::phone.name,
		)

		val attributes = user.attributes.filterKeys { key -> key !in imAttributes }

		return User(
			id = user.id,
			memberOf = group,
			email = user.email ?: "",
			givenName = user.firstName ?: "",
			familyName = user.lastName ?: "",
			address = user.attributes[User::address.name]?.parseJsonTo(Address::class.java).orEmpty(),
			phone = user.attributes[User::phone.name],
			roles = user.roles.assignedRoles,
			rolesComposites = user.roles,
			attributes = attributes,
			enabled = user.enabled,
			disabledBy = user.attributes[User::disabledBy.name],
			creationDate = user.creationDate,
			disabledDate = user.attributes[User::disabledDate.name]?.toLong()
		)
	}
	
}
