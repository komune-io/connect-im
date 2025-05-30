package io.komune.im.f2.user.domain.policies

import io.komune.im.commons.auth.AuthedUserDTO
import io.komune.im.commons.auth.ImPermission
import io.komune.im.commons.auth.hasAcr
import io.komune.im.commons.auth.hasOneOfRoles
import io.komune.im.commons.auth.hasRole
import io.komune.im.commons.model.OrganizationId
import io.komune.im.core.mfa.domain.model.ImMfaPasswordOtpFlowAcr
import io.komune.im.f2.user.domain.model.UserDTO
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("UserPolicies")
object UserPolicies {
    /**
     * User can get a user
     */
    fun canGet(authedUser: AuthedUserDTO, user: UserDTO?): Boolean {
        return authedUser.hasRole(ImPermission.IM_USER_READ)
                || authedUser.id == user?.id
                || user?.memberOf?.id == authedUser.memberOf
    }

    /**
     * User can list users
     */
    fun canPage(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasOneOfRoles(ImPermission.IM_USER_READ)
    }


    /**
     * User can list users ref
     */
    fun checkRefList(authedUser: AuthedUserDTO) = true

    /**
     * User can create a user
     */
    fun canCreate(authedUser: AuthedUserDTO, organizationId: OrganizationId?): Boolean {
        return authedUser.hasRole(ImPermission.IM_USER_WRITE)
            && (organizationId == authedUser.memberOf || authedUser.hasRole(ImPermission.IM_ORGANIZATION_WRITE))
    }

    /**
     * User can update the given user
     */
    fun canUpdate(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return canWriteUser(authedUser, user)
    }

    /**
     * User can configure mfa
     */
    fun canConfigureMfa(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return isMySelf(authedUser, user)
    }

    /**
     * User can disable mfa
     */
    fun canDisableMfa(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return isMySelf(authedUser, user) && !authedUser.hasRole(ImPermission.IM_FORCE_MFA_OTP)
    }

    /**
     * User can disable mfa
     */
    fun canDisableMfaAcr(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return authedUser.hasAcr(ImMfaPasswordOtpFlowAcr.PASSWORD_OTP.key)
    }

    /**
     * User can update member of the given user
     */
    fun canUpdateMemberOf(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImPermission.IM_ORGANIZATION_WRITE)
    }

    /**
     * User can update roles of the given user
     */
    fun canUpdateRole(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImPermission.IM_USER_ROLE_READ)
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
        return (authedUser.id == user.id || authedUser.hasRole(ImPermission.IM_USER_WRITE))
            && (authedUser.memberOf == user.memberOf?.id || authedUser.hasRole(ImPermission.IM_ORGANIZATION_WRITE))
    }

    private fun isNotMySelf(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return !isMySelf(authedUser, user)

    }

    private fun isMySelf(authedUser: AuthedUserDTO, user: UserDTO): Boolean {
        return authedUser.id == user.id

    }
}
