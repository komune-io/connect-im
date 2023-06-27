package city.smartb.im.apikey.api.policies

import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.apikey.domain.policies.ApiKeyPolicies
import city.smartb.im.commons.auth.policies.PolicyEnforcer
import org.springframework.stereotype.Service

@Service
class ApiKeyPoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(apikeyId: ApiKeyId) = check("get apikey") { authedUser ->
        ApiKeyPolicies.canGet(authedUser, apikeyId)
    }

    suspend fun checkPage() = check("page apikeys") { authedUser ->
        ApiKeyPolicies.canList(authedUser)
    }


    suspend fun checkCreate() = check("create an apikey") { authedUser ->
        ApiKeyPolicies.canCreate(authedUser)
    }

    suspend fun apiKeyRemove(apikeyId: ApiKeyId) = check("delete an apikey") { authedUser ->
        ApiKeyPolicies.canDelete(authedUser)
    }

}
