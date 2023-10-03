package city.smartb.im.f2.organization.api

import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.organization.domain.command.OrganizationCreateCommand
import city.smartb.im.f2.organization.domain.command.OrganizationUpdateCommand
import city.smartb.im.f2.organization.domain.model.OrganizationStatusValues
import city.smartb.im.f2.organization.domain.policies.OrganizationPolicies
import city.smartb.im.f2.organization.lib.OrganizationFinderService
import org.springframework.stereotype.Service

@Service
class OrganizationPoliciesEnforcer(
    private val organizationFinderService: OrganizationFinderService
): PolicyEnforcer() {
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

    suspend fun checkDisable() = checkAuthed("disable an organization") { authedUser ->
        OrganizationPolicies.canDisable(authedUser)
    }

    suspend fun checkDelete() = checkAuthed("delete an organization") { authedUser ->
        OrganizationPolicies.canDisable(authedUser)
    }

    suspend fun enforceCommand(command: OrganizationCreateCommand) = enforceAuthed { authedUser ->
        if (!OrganizationPolicies.canUpdateStatus(authedUser)) {
            command.copy(status = OrganizationStatusValues.pending())
        } else {
            command
        }
    }

    suspend fun enforceCommand(command: OrganizationUpdateCommand) = enforceAuthed { authedUser ->
        val organization = organizationFinderService.get(command.id)
        if (!OrganizationPolicies.canUpdateStatus(authedUser)) {
            command.copy(status = organization.status)
        } else {
            command
        }
    }
}
