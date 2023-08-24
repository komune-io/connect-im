package city.smartb.im.apikey.api.policies

import city.smartb.im.apikey.domain.features.query.ApiKeyPageQuery
import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.apikey.domain.policies.ApiKeyPolicies
import city.smartb.im.commons.auth.Roles
import city.smartb.im.commons.auth.hasOneOfRoles
import city.smartb.im.commons.auth.policies.PolicyEnforcer
import org.springframework.stereotype.Service

@Service
class ApiKeyPoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(apikeyId: ApiKeyId) = check("get apikey") { authedUser ->
        ApiKeyPolicies.canGet(authedUser, apikeyId)
    }

    suspend fun enforcePage(query: ApiKeyPageQuery): ApiKeyPageQuery = enforce { authedUser ->
        check("page apikey") {
            ApiKeyPolicies.canPage(it)
        }
        if (authedUser!!.hasOneOfRoles(Roles.SUPER_ADMIN, Roles.ORCHESTRATOR) ) {
            query
        } else {
            query.copy(
                organizationId = authedUser.memberOf
            )
        }
    }


    suspend fun checkCreate() = check("create an apikey") { authedUser ->
        ApiKeyPolicies.canCreate(authedUser)
    }

    suspend fun apiKeyRemove(apikeyId: ApiKeyId) = check("delete an apikey") { authedUser ->
        ApiKeyPolicies.canDelete(authedUser)
    }

}
