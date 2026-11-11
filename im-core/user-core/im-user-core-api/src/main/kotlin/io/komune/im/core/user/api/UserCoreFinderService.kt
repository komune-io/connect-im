package io.komune.im.core.user.api

import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import f2.spring.exception.NotFoundException
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.UserId
import io.komune.im.commons.utils.mapAsync
import io.komune.im.commons.utils.matches
import io.komune.im.commons.utils.page
import io.komune.im.core.commons.CoreService
import io.komune.im.core.user.api.service.UserRepresentationTransformer
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.infra.redis.CacheName
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.springframework.stereotype.Service

@Service
class UserCoreFinderService(
    private val userRepresentationTransformer: UserRepresentationTransformer
) : CoreService(CacheName.User) {

    suspend fun getOrNull(id: UserId): UserModel? = query(id, "Error while fetching user [$id]") {
        val client = keycloakClientProvider.getClient()
        @Suppress("SwallowedException")
        try {
            client.user(id).toRepresentation().let { userRepresentationTransformer.transform(it) }
        } catch (e: JakartaNotFoundException) {
            null
        }
    }

    suspend fun get(id: UserId): UserModel = getOrNull(id) ?: throw NotFoundException(name = "User", id = id)

    suspend fun hasCredentials(id: UserId): Boolean = handleErrors("Error while fetching credentials of user [$id]") {
        keycloakClientProvider.getClient().user(id).credentials().isNotEmpty()
    }

    suspend fun getByEmailOrNull(email: String): UserModel? =
        handleErrors("Error while fetching user by email [$email]") {
            val client = keycloakClientProvider.getClient()
            client.users().search(null, null, null, email, null, null, true, false)
                .firstOrNull { it.email == email }
                .let { userRepresentationTransformer.transform(it) }
        }

    suspend fun page(
        filter: UserCoreFilter = UserCoreFilter(),
        offset: OffsetPagination? = null
    ): PageDTO<UserModel> = with(filter) {
        val client = keycloakClientProvider.getClient()

        val compositeRoles = client.roles().list().mapAsync { role ->
            role.name to client.role(role.name).realmRoleComposites.mapNotNull { it.name }
        }.toMap()

        val users = list(
            ids = ids,
            organizationIds = organizationIds
        )

        users.filter { user ->
            user.id.matches(ids)
                && user.memberOf.matches(organizationIds)
                && (isDisabledIncluded || user.enabled)
                && (attributes == null || attributes.all { (key, value) -> user.attributes[key] == value })
                && (email == null || user.email.contains(email, true))
                && (name == null || "${user.givenName} ${user.familyName}".contains(name, true))
                && user.roles.flatMap { compositeRoles[it].orEmpty() + it }.toSet().matches(roles)
        }.sortedByDescending(UserModel::creationDate)
            .page(offset)
    }

    private suspend fun list(
        ids: Collection<UserId>? = null,
        organizationIds: Collection<OrganizationId>? = null
    ): List<UserModel> {
        val client = keycloakClientProvider.getClient()
        return when {
            ids != null -> {
                ids.mapAsync(::getOrNull).filterNotNull()
            }

            organizationIds != null -> {
                organizationIds.mapAsync { organizationId ->
                    client.group(organizationId)
                        .members(0, Int.MAX_VALUE, false)
                        .filter { it.serviceAccountClientId == null }
                }.flatten().let { userRepresentationTransformer.transform(it) }
            }

            else -> {
                client.users()
                    .search("", 0, Int.MAX_VALUE, false)
                    .let { userRepresentationTransformer.transform(it) }
            }
        }.filter { !it.isApiKey }
    }
}

/** What [UserCoreFinderService.page] keeps; a null criterion keeps everything. */
data class UserCoreFilter(
    val ids: Collection<UserId>? = null,
    val organizationIds: Collection<OrganizationId>? = null,
    val name: String? = null,
    val email: String? = null,
    val roles: Collection<RoleIdentifier>? = null,
    val attributes: Map<String, String>? = null,
    val isDisabledIncluded: Boolean = false,
)
