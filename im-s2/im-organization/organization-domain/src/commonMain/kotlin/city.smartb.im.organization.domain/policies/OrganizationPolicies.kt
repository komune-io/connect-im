package city.smartb.im.organization.domain.policies

import city.smartb.im.commons.auth.AuthedUserDTO
import city.smartb.im.commons.auth.Roles
import city.smartb.im.commons.auth.hasRole
import city.smartb.im.organization.domain.model.OrganizationId
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("OrganizationPolicies")
object OrganizationPolicies {
    /**
     * User can get an organization
     */
    fun canGet(authedUser: AuthedUserDTO, organizationId: String): Boolean {
        return authedUser.hasRole(Roles.IM_ORGANIZATION_READ) || authedUser.memberOf == organizationId
    }

    /**
     * User can list organizations
     */
    fun canList(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(Roles.IM_ORGANIZATION_READ)
    }

    /**
     * User can list organizations ref
     */
    fun checkRefList(authedUser: AuthedUserDTO) = true

    /**
     * User can create an organization
     */
    fun canCreate(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(Roles.IM_ORGANIZATION_WRITE)
    }

    /**
     * User can update the given organization
     */
    fun canUpdate(authedUser: AuthedUserDTO, organizationId: OrganizationId): Boolean {
        return authedUser.hasRole(Roles.IM_ORGANIZATION_WRITE)
                || authedUser.hasRole(Roles.IM_MY_ORGANIZATION_WRITE) && authedUser.memberOf == organizationId
    }

    /**
     * User can disable an organization
     */
    fun canDisable(authedUser: AuthedUserDTO, organizationId: OrganizationId): Boolean {
        return authedUser.hasRole(Roles.IM_ORGANIZATION_WRITE)
    }

    /**
     * User can delete an organization
     */
    fun canDelete(authedUser: AuthedUserDTO, organizationId: OrganizationId): Boolean {
        return authedUser.hasRole(Roles.IM_ORGANIZATION_WRITE)
    }

}
