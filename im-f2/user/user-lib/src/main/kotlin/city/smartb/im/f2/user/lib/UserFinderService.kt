package city.smartb.im.f2.user.lib

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId
import city.smartb.im.core.user.api.UserCoreFinderService
import city.smartb.im.f2.user.domain.model.User
import city.smartb.im.f2.user.lib.service.UserToDTOTransformer
import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val userCoreFinderService: UserCoreFinderService,
    private val userToDTOTransformer: UserToDTOTransformer
) {
    suspend fun getOrNull(id: UserId): User? {
        return userCoreFinderService.getOrNull(id).let { userToDTOTransformer.transform(it) }
    }

    suspend fun get(id: UserId): User {
        return userCoreFinderService.get(id).let { userToDTOTransformer.transform(it) }
    }

    suspend fun getByEmailOrNull(email: String): User? {
        return userCoreFinderService.getByEmailOrNull(email).let { userToDTOTransformer.transform(it) }
    }

    suspend fun page(
        ids: Collection<UserId>? = null,
        organizationIds: Collection<OrganizationId>? = null,
        name: String? = null,
        email: String? = null,
        roles: Collection<RoleIdentifier>? = null,
        attributes: Map<String, String>? = null,
        withDisabled: Boolean = false,
        offset: OffsetPagination? = null
    ): PageDTO<User> {
        return userCoreFinderService.page(
            ids = ids,
            organizationIds = organizationIds,
            name = name,
            email = email,
            roles = roles,
            attributes = attributes,
            withDisabled = withDisabled,
            offset = offset
        ).let { userToDTOTransformer.transform(it) }
    }
}
