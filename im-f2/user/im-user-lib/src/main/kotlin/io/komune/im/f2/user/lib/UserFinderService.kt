package io.komune.im.f2.user.lib

import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.UserId
import io.komune.im.core.organization.api.OrganizationCoreFilter
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.organization.domain.model.OrganizationModel
import io.komune.im.core.user.api.UserCoreFilter
import io.komune.im.core.user.api.UserCoreFinderService
import io.komune.im.f2.user.domain.model.User
import io.komune.im.f2.user.lib.service.UserToDTOTransformer
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val userCoreFinderService: UserCoreFinderService,
    private val userToDTOTransformer: UserToDTOTransformer
) {
    suspend fun getOrNull(id: UserId): User? =
        userCoreFinderService.getOrNull(id).let { userToDTOTransformer.transform(it) }

    suspend fun get(id: UserId): User = userCoreFinderService.get(id).let { userToDTOTransformer.transform(it) }

    suspend fun getByEmailOrNull(email: String): User? =
        userCoreFinderService.getByEmailOrNull(email).let { userToDTOTransformer.transform(it) }

    suspend fun page(
        filter: UserFilter = UserFilter(),
        offset: OffsetPagination? = null
    ): PageDTO<User> {
        val organizations = filter.organizationName?.let { organizationName ->
            organizationCoreFinderService.page(
                OrganizationCoreFilter(ids = filter.organizationIds, identifier = organizationName)
            ).items
        }

        return userCoreFinderService.page(
            UserCoreFilter(
                ids = filter.ids,
                organizationIds = organizations?.map(OrganizationModel::id) ?: filter.organizationIds,
                name = filter.name,
                email = filter.email,
                roles = filter.roles,
                attributes = filter.attributes,
                isDisabledIncluded = filter.isDisabledIncluded,
            ),
            offset = offset
        ).let { userToDTOTransformer.transform(it) }
    }
}

/** What [UserFinderService.page] keeps; a null criterion keeps everything. */
data class UserFilter(
    val ids: Collection<UserId>? = null,
    val organizationIds: Collection<OrganizationId>? = null,
    val organizationName: String? = null,
    val name: String? = null,
    val email: String? = null,
    val roles: Collection<RoleIdentifier>? = null,
    val attributes: Map<String, String>? = null,
    val isDisabledIncluded: Boolean = false,
)
