package city.smartb.im.user.api.policies

import city.smartb.im.commons.auth.Role
import city.smartb.im.commons.auth.exception.ForbiddenAccessException
import city.smartb.im.commons.auth.hasOneOfRoles
import city.smartb.im.commons.auth.hasRole
import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.domain.model.UserDTO
import city.smartb.im.user.domain.model.UserId
import city.smartb.im.user.domain.policies.UserPolicies
import org.springframework.stereotype.Service

@Service
class UserPoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(user: UserDTO? = null) = check("get user") { authedUser ->
        UserPolicies.canGet(authedUser, user)
    }


    suspend fun enforcePage(query: UserPageQuery): UserPageQuery = enforce { authedUser ->
        if(!UserPolicies.canPage(authedUser)) {
            throw ForbiddenAccessException("page user")
        }
        if(authedUser.hasOneOfRoles(Role.SUPER_ADMIN, Role.ORCHESTRATOR)) {
            query
        } else {
            query.copy(
                organizationId = authedUser.memberOf
            )
        }
    }

    suspend fun checkRefList() = check("list user refs") { authedUser ->
        UserPolicies.checkRefList(authedUser)
    }

    suspend fun checkCreate() = check("create an user") { authedUser ->
        UserPolicies.canCreate(authedUser)
    }

    suspend fun checkUpdate(userId: UserId) = check("update an user") { authedUser ->
        UserPolicies.canUpdate(authedUser, userId)
    }

    suspend fun checkDisable(userId: UserId) = check("disable an user") { authedUser ->
        UserPolicies.canDisable(authedUser, userId)
    }

    suspend fun checkDelete(userId: UserId) = check("delete an user") { authedUser ->
        UserPolicies.canDisable(authedUser, userId)
    }

}
