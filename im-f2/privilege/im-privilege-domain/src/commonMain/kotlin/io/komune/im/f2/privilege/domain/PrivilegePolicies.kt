package io.komune.im.f2.privilege.domain

import io.komune.im.commons.auth.AuthedUserDTO
import io.komune.im.commons.auth.ImPermission
import io.komune.im.commons.auth.hasRole
import kotlin.js.JsExport

@JsExport
object PrivilegePolicies {
    fun canGet(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImPermission.IM_ROLE_READ)
    }

    fun canList(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImPermission.IM_ROLE_READ)
    }

    fun canDefine(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImPermission.IM_ROLE_WRITE)
    }
}
