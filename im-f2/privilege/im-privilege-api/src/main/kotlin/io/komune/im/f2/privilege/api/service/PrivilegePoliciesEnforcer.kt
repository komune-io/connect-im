package io.komune.im.f2.privilege.api.service

import io.komune.im.commons.auth.policies.PolicyEnforcer
import io.komune.im.f2.privilege.domain.PrivilegePolicies
import org.springframework.stereotype.Service

@Service
class PrivilegePoliciesEnforcer: PolicyEnforcer() {
    suspend fun checkGet() = checkAuthed("get a privilege") { authedUser ->
        PrivilegePolicies.canGet(authedUser)
    }

    suspend fun checkList() = checkAuthed("list privileges") { authedUser ->
        PrivilegePolicies.canList(authedUser)
    }

    suspend fun checkDefine() = checkAuthed("define a privilege") { authedUser ->
        PrivilegePolicies.canDefine(authedUser)
    }
}
