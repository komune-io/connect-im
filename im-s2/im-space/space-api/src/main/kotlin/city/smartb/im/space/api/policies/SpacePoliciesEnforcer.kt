package city.smartb.im.space.api.policies

import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.space.domain.model.SpaceId
import city.smartb.im.space.domain.policies.SpacePolicies
import org.springframework.stereotype.Service

@Service
class SpacePoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(spaceId: SpaceId) = check("get space") { authedUser ->
        SpacePolicies.canGet(authedUser, spaceId)
    }

    suspend fun checkPage() = check("page spaces") { authedUser ->
        SpacePolicies.canPage(authedUser)
    }

    suspend fun checkCreate() = check("create an space") { authedUser ->
        SpacePolicies.canCreate(authedUser)
    }

    suspend fun checkDelete(spaceId: SpaceId) = check("delete an space") { authedUser ->
        SpacePolicies.canDelete(authedUser, spaceId)
    }

}
