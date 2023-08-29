package city.smartb.im.apikey.domain.policies

import city.smartb.im.commons.auth.AuthedUserDTO
import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.auth.hasRole
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("ApiKeyPolicies")
object ApiKeyPolicies {
    /**
     * User can get an apikey
     */
    fun canGet(authedUser: AuthedUserDTO, apikeyId: String): Boolean {
        return authedUser.hasRole(ImRole.IM_APIKEY_READ) || authedUser.memberOf == apikeyId
    }

    /**
     * User can list apikeys
     */
    fun canPage(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_APIKEY_READ) || authedUser.hasRole(ImRole.SUPER_ADMIN)
    }

    /**
     * User can create an apikey
     */
    fun canCreate(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_APIKEY_WRITE)
    }

    /**
     * User can delete an apikey
     */
    fun canDelete(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_APIKEY_WRITE)
    }

}
