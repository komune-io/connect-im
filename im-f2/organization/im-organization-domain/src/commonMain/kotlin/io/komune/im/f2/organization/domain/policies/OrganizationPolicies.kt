package io.komune.im.f2.organization.domain.policies

import io.komune.im.commons.auth.AuthedUserDTO
import io.komune.im.commons.auth.ImRole
import io.komune.im.commons.auth.hasRole
import io.komune.im.commons.model.OrganizationId
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("OrganizationPolicies")
object OrganizationPolicies {
    /**
     * User can get an organization
     */
    fun canGet(authedUser: AuthedUserDTO, organizationId: String): Boolean {
        return authedUser.hasRole(ImRole.IM_ORGANIZATION_READ) || authedUser.memberOf == organizationId
    }

    /**
     * User can list organizations
     */
    fun canList(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_ORGANIZATION_READ)
    }

    /**
     * User can list organizations ref
     */
    fun checkRefList(authedUser: AuthedUserDTO?) = authedUser != null

    /**
     * User can create an organization
     */
    fun canCreate(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_ORGANIZATION_WRITE)
    }

    /**
     * User can update the given organization
     */
    fun canUpdate(authedUser: AuthedUserDTO, organizationId: OrganizationId): Boolean {
        return authedUser.hasRole(ImRole.IM_ORGANIZATION_WRITE)
                || authedUser.hasRole(ImRole.IM_MY_ORGANIZATION_WRITE) && authedUser.memberOf == organizationId
    }

    fun canUpdateStatus(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.ORCHESTRATOR)
    }

    /**
     * User can disable an organization
     */
    fun canDisable(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_ORGANIZATION_WRITE)
    }

    /**
     * User can delete an organization
     */
    fun canDelete(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_ORGANIZATION_WRITE)
    }

}
