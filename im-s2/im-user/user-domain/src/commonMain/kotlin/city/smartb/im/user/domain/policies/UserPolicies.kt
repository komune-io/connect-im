package city.smartb.im.user.domain.policies

import city.smartb.im.commons.auth.AuthedUserDTO
import city.smartb.im.commons.auth.Role
import city.smartb.im.commons.auth.hasOneOfRoles
import city.smartb.im.commons.auth.hasRole
import city.smartb.im.user.domain.model.UserDTO
import city.smartb.im.user.domain.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("UserPolicies")
object UserPolicies {
    /**
     * User can get an user
     */
    fun canGet(authedUser: AuthedUserDTO, user: UserDTO?): Boolean {
        return authedUser.hasRole(Role.IM_USER_READ)
                || authedUser.id == user?.id
                || user?.memberOf?.id == authedUser.memberOf
    }

    /**
     * User can list users
     */
    fun canPage(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasOneOfRoles(Role.IM_USER_READ)
    }


    /**
     * User can list users ref
     */
    fun checkRefList(authedUser: AuthedUserDTO) = true

    /**
     * User can create an user
     */
    fun canCreate(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(Role.IM_USER_WRITE)
    }

    /**
     * User can update the given user
     */
    fun canUpdate(authedUser: AuthedUserDTO, userId: UserId): Boolean {
        return authedUser.hasRole(Role.IM_USER_WRITE)
                || authedUser.id == userId
    }

    /**
     * User can disable an user
     */
    fun canDisable(authedUser: AuthedUserDTO, userId: UserId): Boolean {
        return authedUser.hasRole(Role.IM_USER_WRITE)
    }

    /**
     * User can delete an user
     */
    fun canDelete(authedUser: AuthedUserDTO, userId: UserId): Boolean {
        return authedUser.hasRole(Role.IM_USER_WRITE)
    }
}
