package city.smartb.im.f2.privilege.api.service

import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.f2.privilege.domain.PrivilegePolicies
import org.springframework.stereotype.Service

@Service
class PrivilegePoliciesEnforcer: PolicyEnforcer() {
    suspend fun checkGet() = checkAuthed("get a privilege") { authedUser ->
        PrivilegePolicies.canGet(authedUser)
    }

    suspend fun checkDefine() = checkAuthed("define a privilege") { authedUser ->
        PrivilegePolicies.canDefine(authedUser)
    }
}
