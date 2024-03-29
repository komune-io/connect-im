package city.smartb.im.apikey.api

import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.apikey.domain.policies.ApiKeyPolicies
import city.smartb.im.apikey.domain.query.ApiKeyPageQuery
import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.auth.hasOneOfRoles
import city.smartb.im.commons.auth.policies.PolicyEnforcer
import org.springframework.stereotype.Service

@Service
class ApiKeyPoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(apikeyId: ApiKeyId) = checkAuthed("get apikey") { authedUser ->
        ApiKeyPolicies.canGet(authedUser, apikeyId)
    }

    suspend fun checkPage() = checkAuthed("page apikeys") { authedUser ->
        ApiKeyPolicies.canPage(authedUser)
    }

    suspend fun checkCreate() = checkAuthed("create an apikey") { authedUser ->
        ApiKeyPolicies.canCreate(authedUser)
    }

    suspend fun checkRemove(apikeyId: ApiKeyId) = checkAuthed("delete an apikey") { authedUser ->
        ApiKeyPolicies.canDelete(authedUser)
    }

    suspend fun enforcePage(query: ApiKeyPageQuery): ApiKeyPageQuery = enforceAuthed { authedUser ->
        if (authedUser.hasOneOfRoles(ImRole.ORCHESTRATOR) ) {
            query
        } else {
            query.copy(
                organizationId = authedUser.memberOf
            )
        }
    }
}
