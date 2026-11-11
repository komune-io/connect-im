package io.komune.im.f2.organization.domain.policies

import io.komune.im.commons.auth.AuthedUserDTO
import io.komune.im.commons.auth.ImPermission
import io.komune.im.commons.auth.hasOneOfRoles
import io.komune.im.commons.auth.hasRole
import io.komune.im.commons.model.OrganizationId
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("OrganizationPolicies")
object OrganizationPolicies {
    /**
     * User can get an organization.
     */
    fun canGet(authedUser: AuthedUserDTO, organizationId: String): Boolean {
        return authedUser.hasRole(ImPermission.IM_ORGANIZATION_READ)
                || authedUser.hasRole(ImPermission.IM_MY_ORGANIZATION_READ) && authedUser.memberOf == organizationId
    }

    /**
     * User can list organizations.
     */
    fun canList(authedUser: AuthedUserDTO): Boolean = authedUser.hasRole(ImPermission.IM_ORGANIZATION_READ)

    /**
     * User can list organizations ref.
     */
    fun checkRefList(authedUser: AuthedUserDTO?) = authedUser != null
            && authedUser.hasOneOfRoles(ImPermission.IM_ORGANIZATION_READ, ImPermission.IM_MY_ORGANIZATION_READ)

    /**
     * User can create an organization.
     */
    fun canCreate(authedUser: AuthedUserDTO): Boolean = authedUser.hasRole(ImPermission.IM_ORGANIZATION_WRITE)

    /**
     * User can update the given organization.
     */
    fun canUpdate(authedUser: AuthedUserDTO, organizationId: OrganizationId): Boolean {
        return authedUser.hasRole(ImPermission.IM_ORGANIZATION_WRITE)
                || authedUser.hasRole(ImPermission.IM_MY_ORGANIZATION_WRITE) && authedUser.memberOf == organizationId
    }

    fun canUpdateStatus(authedUser: AuthedUserDTO): Boolean =
        authedUser.hasRole(ImPermission.IM_ORGANIZATION_STATUS_WRITE)

    /**
     * User can disable an organization.
     */
    fun canDisable(authedUser: AuthedUserDTO): Boolean = authedUser.hasRole(ImPermission.IM_ORGANIZATION_WRITE)

    /**
     * User can delete an organization.
     */
    fun canDelete(authedUser: AuthedUserDTO): Boolean = authedUser.hasRole(ImPermission.IM_ORGANIZATION_WRITE)

}
