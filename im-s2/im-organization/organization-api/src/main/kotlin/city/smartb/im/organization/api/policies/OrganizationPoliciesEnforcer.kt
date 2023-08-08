package city.smartb.im.organization.api.policies

import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.organization.domain.policies.OrganizationPolicies
import org.springframework.stereotype.Service

@Service
class OrganizationPoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(organizationId: OrganizationId) = check("get organization") { authedUser ->
        OrganizationPolicies.canGet(authedUser, organizationId)
    }
    suspend fun checkList() = check("list organizations") { authedUser ->
        OrganizationPolicies.canList(authedUser)
    }
    suspend fun checkPage() = check("page organizations") { authedUser ->
        OrganizationPolicies.canList(authedUser)
    }

    suspend fun checkRefList() = check("list organization refs") { authedUser ->
        OrganizationPolicies.checkRefList(authedUser)
    }

    suspend fun checkCreate() = check("create an organization") { authedUser ->
        OrganizationPolicies.canCreate(authedUser)
    }

    suspend fun checkUpdate(organizationId: OrganizationId) = check("update an organization") { authedUser ->
        OrganizationPolicies.canUpdate(authedUser, organizationId)
    }


    suspend fun checkDisable(organizationId: OrganizationId) = check("disable an organization") { authedUser ->
        OrganizationPolicies.canDisable(authedUser, organizationId)
    }

    suspend fun checkDelete(organizationId: OrganizationId) = check("delete an organization") { authedUser ->
        OrganizationPolicies.canDisable(authedUser, organizationId)
    }

}
