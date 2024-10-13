package io.komune.im.f2.space.domain.policies

import io.komune.im.commons.auth.AuthedUserDTO
import io.komune.im.commons.auth.ImRole
import io.komune.im.commons.auth.hasRole
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("SpacePolicies")
object SpacePolicies {
    /**
     * User can get a space
     */
    fun canGet(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_SPACE_READ)
    }

    /**
     * User can list spaces
     */
    fun canPage(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_SPACE_READ)
    }

    /**
     * User can create a space
     */
    fun canDefine(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_SPACE_WRITE)
    }

    /**
     * User can delete a space
     */
    fun canDelete(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_SPACE_WRITE)
    }

}
