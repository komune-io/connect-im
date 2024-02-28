package io.komune.im.apikey.api

import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.apikey.domain.policies.ApiKeyPolicies
import io.komune.im.apikey.domain.query.ApiKeyPageQuery
import io.komune.im.commons.auth.ImRole
import io.komune.im.commons.auth.hasOneOfRoles
import io.komune.im.commons.auth.policies.PolicyEnforcer
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
