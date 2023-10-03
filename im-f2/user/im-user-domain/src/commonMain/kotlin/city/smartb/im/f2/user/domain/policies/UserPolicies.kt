package city.smartb.im.f2.user.domain.policies

import city.smartb.im.commons.auth.AuthedUserDTO
import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.auth.hasOneOfRoles
import city.smartb.im.commons.auth.hasRole
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.user.domain.model.UserDTO
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("UserPolicies")
object UserPolicies {
    /**
     * User can get a user
     */
    fun canGet(authedUser: AuthedUserDTO, user: UserDTO?): Boolean {
        return authedUser.hasRole(ImRole.IM_USER_READ)
                || authedUser.id == user?.id
                || user?.memberOf?.id == authedUser.memberOf
    }

    /**
     * User can list users
     */
    fun canPage(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasOneOfRoles(ImRole.IM_USER_READ)
    }


    /**
     * User can list users ref
     */
    fun checkRefList(authedUser: AuthedUserDTO) = true

    /**
     * User can create a user
     */
    fun canCreate(authedUser: AuthedUserDTO, organizationId: OrganizationId?): Boolean {
        return authedUser.hasRole(ImRole.IM_USER_WRITE)
            && (organizationId == authedUser.memberOf || authedUser.hasRole(ImRole.IM_ORGANIZATION_WRITE))
    }

    /**
     * User can update the given user
     */
    fun canUpdate(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return canWriteUser(authedUser, user)
    }

    /**
     * User can disable a user
     */
    fun canDisable(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return canWriteUser(authedUser, user) && isNotMySelf(authedUser, user)
    }

    /**
     * User can delete a user
     */
    fun canDelete(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return canWriteUser(authedUser, user) && isNotMySelf(authedUser, user)
    }

    private fun canWriteUser(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return (authedUser.id == user.id || authedUser.hasRole(ImRole.IM_USER_WRITE))
            && (authedUser.memberOf == user.memberOf?.id || authedUser.hasRole(ImRole.IM_ORGANIZATION_WRITE))
    }
    private fun isNotMySelf(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return authedUser.id != user.id

    }
}
