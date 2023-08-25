package city.smartb.im.user.api.policies

import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.auth.hasOneOfRoles
import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.domain.model.UserDTO
import city.smartb.im.user.domain.model.UserId
import city.smartb.im.user.domain.policies.UserPolicies
import org.springframework.stereotype.Service

@Service
class UserPoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(user: UserDTO? = null) = checkAuthed("get user") { authedUser ->
        UserPolicies.canGet(authedUser, user)
    }

    suspend fun checkPage() = checkAuthed("page users") { authedUser ->
        UserPolicies.canPage(authedUser)
    }

    suspend fun checkRefList() = checkAuthed("list user refs") { authedUser ->
        UserPolicies.checkRefList(authedUser)
    }

    suspend fun checkCreate() = checkAuthed("create an user") { authedUser ->
        UserPolicies.canCreate(authedUser)
    }

    suspend fun checkUpdate(userId: UserId) = checkAuthed("update an user") { authedUser ->
        UserPolicies.canUpdate(authedUser, userId)
    }

    suspend fun checkDisable(userId: UserId) = checkAuthed("disable an user") { authedUser ->
        UserPolicies.canDisable(authedUser, userId)
    }

    suspend fun checkDelete(userId: UserId) = checkAuthed("delete an user") { authedUser ->
        UserPolicies.canDisable(authedUser, userId)
    }

    suspend fun enforcePage(query: UserPageQuery): UserPageQuery = enforceAuthed { authedUser ->
        checkPage()

        if (authedUser.hasOneOfRoles(ImRole.SUPER_ADMIN, ImRole.ORCHESTRATOR)) {
            query
        } else {
            query.copy(
                organizationId = authedUser.memberOf
            )
        }
    }
}
