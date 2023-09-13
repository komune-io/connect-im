package city.smartb.im.commons.auth

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("AuthedUserDTO")
interface AuthedUserDTO {
    val id: UserId
    val identifier: String?
    val memberOf: OrganizationId?
    val roles: Array<String>
}

data class AuthedUser(
    override val id: UserId,
    override val identifier: String?,
    override val memberOf: OrganizationId?,
    override val roles: Array<String>
): AuthedUserDTO

fun AuthedUserDTO.hasRole(role: String) = role in roles
fun AuthedUserDTO.hasRole(role: ImRole) = hasRole(role.identifier)
fun AuthedUserDTO.hasRoles(vararg roles: String) = roles.all(this.roles::contains)
fun AuthedUserDTO.hasRoles(vararg roles: ImRole) = hasRoles(*roles.map(ImRole::identifier).toTypedArray())
fun AuthedUserDTO.hasOneOfRoles(vararg roles: String) = roles.any(this.roles::contains)
fun AuthedUserDTO.hasOneOfRoles(vararg roles: ImRole) = hasOneOfRoles(*roles.map(ImRole::identifier).toTypedArray())
