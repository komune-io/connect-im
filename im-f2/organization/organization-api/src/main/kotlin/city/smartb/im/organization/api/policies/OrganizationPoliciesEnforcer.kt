package city.smartb.im.organization.api.policies

import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.organization.domain.policies.OrganizationPolicies
import org.springframework.stereotype.Service

@Service
class OrganizationPoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(organizationId: OrganizationId) = checkAuthed("get organization") { authedUser ->
        OrganizationPolicies.canGet(authedUser, organizationId)
    }

    suspend fun checkList() = checkAuthed("list organizations") { authedUser ->
        OrganizationPolicies.canList(authedUser)
    }
    suspend fun checkPage() = checkAuthed("page organizations") { authedUser ->
        OrganizationPolicies.canList(authedUser)
    }

    suspend fun checkRefList() = checkAuthed("list organization refs") { authedUser ->
        OrganizationPolicies.checkRefList(authedUser)
    }

    suspend fun checkCreate() = checkAuthed("create an organization") { authedUser ->
        OrganizationPolicies.canCreate(authedUser)
    }

    suspend fun checkUpdate(organizationId: OrganizationId) = checkAuthed("update an organization") { authedUser ->
        OrganizationPolicies.canUpdate(authedUser, organizationId)
    }


    suspend fun checkDisable(organizationId: OrganizationId) = checkAuthed("disable an organization") { authedUser ->
        OrganizationPolicies.canDisable(authedUser, organizationId)
    }

    suspend fun checkDelete(organizationId: OrganizationId) = checkAuthed("delete an organization") { authedUser ->
        OrganizationPolicies.canDisable(authedUser, organizationId)
    }

}
