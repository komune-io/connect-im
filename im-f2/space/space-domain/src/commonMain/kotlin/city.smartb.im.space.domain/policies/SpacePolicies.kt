package city.smartb.im.space.domain.policies

import city.smartb.im.commons.auth.AuthedUserDTO
import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.auth.hasRole
import city.smartb.im.space.domain.model.SpaceId
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("SpacePolicies")
object SpacePolicies {
    /**
     * User can get a space
     */
    fun canGet(authedUser: AuthedUserDTO, spaceId: String): Boolean {
        return authedUser.hasRole(ImRole.IM_SPACE_READ) || authedUser.memberOf == spaceId
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
    fun canCreate(authedUser: AuthedUserDTO): Boolean {
        return authedUser.hasRole(ImRole.IM_SPACE_WRITE)
    }

    /**
     * User can delete a space
     */
    fun canDelete(authedUser: AuthedUserDTO, spaceId: SpaceId): Boolean {
        return authedUser.hasRole(ImRole.IM_SPACE_WRITE)
    }

}
