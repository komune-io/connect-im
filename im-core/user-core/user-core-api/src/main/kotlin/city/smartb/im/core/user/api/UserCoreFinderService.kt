package city.smartb.im.core.user.api

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId
import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.commons.utils.matches
import city.smartb.im.commons.utils.page
import city.smartb.im.core.commons.CoreService
import city.smartb.im.core.user.api.service.UserRepresentationTransformer
import city.smartb.im.core.user.domain.model.User
import city.smartb.im.infra.redis.CacheName
import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.PageDTO
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class UserCoreFinderService(
    private val userRepresentationTransformer: UserRepresentationTransformer
): CoreService(CacheName.User) {

    suspend fun getOrNull(id: UserId): User? = query(id, "Error while fetching user [$id]") {
        val client = keycloakClientProvider.get()
        try {
            client.user(id).toRepresentation().let { userRepresentationTransformer.transform(it) }
        } catch (e: javax.ws.rs.NotFoundException) {
            null
        }
    }

    suspend fun get(id: UserId): User {
        return getOrNull(id) ?: throw NotFoundException("User", id)
    }

    suspend fun getByEmailOrNull(email: String): User? = handleErrors("Error while fetching user by email [$email]") {
        val client = keycloakClientProvider.get()
        client.users().search(null, null, null, email, null, null, true, false)
            .firstOrNull { it.email == email }
            .let { userRepresentationTransformer.transform(it) }
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
        val client = keycloakClientProvider.get()

        val compositeRoles = client.roles().list().mapAsync { role ->
            role.name to client.role(role.name).realmRoleComposites.mapNotNull { it.name }
        }.toMap()

        val users = when {
            ids != null -> {
                ids.mapAsync(::getOrNull).filterNotNull()
            }
            organizationIds != null -> {
                organizationIds.mapAsync { organizationId ->
                    client.group(organizationId).members(0, Int.MAX_VALUE, false)
                }.flatten().let { userRepresentationTransformer.transform(it) }
            }
            else -> {
                client.users()
                    .search("", 0, Int.MAX_VALUE, false)
                    .let { userRepresentationTransformer.transform(it) }
            }
        }

        return users.filter { user ->
            user.id.matches(ids)
                && user.memberOf.matches(organizationIds)
                && (withDisabled || user.enabled)
                && (attributes == null || attributes.all { (key, value) -> user.attributes[key] == value })
                && (email == null || user.email.contains(email, true))
                && (name == null || ("${user.givenName} ${user.familyName}").contains(name, true))
                && (user.roles.flatMap { compositeRoles[it].orEmpty() + it }.toSet().matches(roles))
        }.sortedByDescending(User::creationDate)
            .page(offset)
    }
}
