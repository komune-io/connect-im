package io.komune.im.f2.space.api.service

import io.komune.im.commons.auth.policies.PolicyEnforcer
import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.f2.space.domain.policies.SpacePolicies
import org.springframework.stereotype.Service

@Service
class SpacePoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(spaceIdentifier: SpaceIdentifier) = checkAuthed("get space") { authedUser ->
        SpacePolicies.canGet(authedUser)
    }

    suspend fun checkPage() = checkAuthed("page spaces") { authedUser ->
        SpacePolicies.canPage(authedUser)
    }

    suspend fun checkDefine() = checkAuthed("define a space") { authedUser ->
        SpacePolicies.canDefine(authedUser)
    }

    suspend fun checkDelete(spaceIdentifier: SpaceIdentifier) = checkAuthed("delete a space") { authedUser ->
        SpacePolicies.canDelete(authedUser, spaceIdentifier)
    }

}
