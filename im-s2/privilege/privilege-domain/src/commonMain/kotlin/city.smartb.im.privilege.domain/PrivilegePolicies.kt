package city.smartb.im.privilege.domain

import city.smartb.im.commons.auth.AuthedUserDTO
import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.auth.hasRole
import kotlin.js.JsExport

@JsExport
object PrivilegePolicies {
    fun canGet(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_ROLE_READ)
    }

    fun canDefine(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_ROLE_WRITE)
    }
}
