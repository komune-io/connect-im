package io.komune.im.f2.user.lib.service

import io.komune.im.commons.Transformer
import io.komune.im.commons.model.Address
import io.komune.im.commons.utils.EmptyAddress
import io.komune.im.commons.utils.mapNotNullAsync
import io.komune.im.commons.utils.parseJsonTo
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import io.komune.im.f2.user.domain.model.User
import org.springframework.stereotype.Service

@Service
class UserToDTOTransformer(
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val privilegeFinderService: PrivilegeFinderService,
): Transformer<UserModel, User>() {
    companion object {
        val IM_USER_ATTRIBUTES = listOf(
            User::address.name,
            User::disabledBy.name,
            User::disabledDate.name,
            User::phone.name,
            UserModel::isApiKey.name
        )
    }

    override suspend fun transform(item: UserModel): User {
        val roles = item.roles.mapNotNullAsync(privilegeFinderService::getRoleOrNull)
        val attributes = item.attributes.filterKeys { key -> key !in IM_USER_ATTRIBUTES }
        val organizationRef = item.memberOf?.let {
            organizationCoreFinderService.get(it)
        }?.let {
            OrganizationRef(
                id = it.id,
                name = it.identifier,
                roles = it.roles
            )
        }

		return User(
			id = item.id,
			memberOf = organizationRef,
			email = item.email,
			givenName = item.givenName,
			familyName = item.familyName,
			address = item.attributes[User::address.name]?.parseJsonTo(Address::class.java) ?: EmptyAddress,
			phone = item.attributes[User::phone.name],
			roles = roles,
			attributes = attributes,
			enabled = item.enabled,
			disabledBy = item.attributes[User::disabledBy.name],
			creationDate = item.creationDate,
			disabledDate = item.attributes[User::disabledDate.name]?.toLong()
		)
	}
	
}
