package io.komune.im.f2.user.api

import io.komune.im.commons.auth.ImRole
import io.komune.im.commons.auth.hasOneOfRoles
import io.komune.im.commons.auth.policies.PolicyEnforcer
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.UserId
import io.komune.im.f2.user.domain.model.UserDTO
import io.komune.im.f2.user.domain.policies.UserPolicies
import io.komune.im.f2.user.domain.query.UserPageQuery
import io.komune.im.f2.user.lib.UserFinderService
import org.springframework.stereotype.Service

@Service
class UserPoliciesEnforcer(
    private val userFinderService: UserFinderService
): PolicyEnforcer() {

    suspend fun checkGet(user: UserDTO? = null) = checkAuthed("get user") { authedUser ->
        UserPolicies.canGet(authedUser, user)
    }

    suspend fun checkPage() = checkAuthed("page users") { authedUser ->
        UserPolicies.canPage(authedUser)
    }

    suspend fun checkRefList() = checkAuthed("list user refs") { authedUser ->
        UserPolicies.checkRefList(authedUser)
    }

    suspend fun checkCreate(organizationId: OrganizationId?) = checkAuthed("create an user") { authedUser ->
        UserPolicies.canCreate(authedUser, organizationId)
    }

    suspend fun checkUpdate(userId: UserId) = checkAuthed("update an user") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canUpdate(authedUser, user)
    }

    suspend fun checkDisable(userId: UserId) = checkAuthed("disable an user") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canDisable(authedUser, user)
    }

    suspend fun checkDelete(userId: UserId) = checkAuthed("delete an user") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canDelete(authedUser, user)
    }

    suspend fun enforcePageQuery(query: UserPageQuery): UserPageQuery = enforceAuthed { authedUser ->
        if (authedUser.hasOneOfRoles(ImRole.ORCHESTRATOR)) {
            query
        } else {
            query.copy(
                organizationId = authedUser.memberOf
            )
        }
    }
}
