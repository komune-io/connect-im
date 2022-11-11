package city.smartb.im.commons.auth

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Identifier of an organization
 * @d2 model
 * @visual json "c790642c-4ed2-4cfc-bc45-905a39006e99"
 */
typealias OrganizationId = String

/**
 * Identifier of a user
 * @d2 model
 * @visual json "ad4adcc1-2633-4f2b-8b66-aaca39f45146"
 */
typealias UserId = String

@JsExport
@JsName("AuthedUserDTO")
interface AuthedUserDTO {
    val id: UserId
    val memberOf: OrganizationId?
    val roles: Array<String>
}

data class AuthedUser(
    override val id: UserId,
    override val memberOf: OrganizationId?,
    override val roles: Array<String>
): AuthedUserDTO

fun AuthedUserDTO.hasRole(role: String) = role in roles
fun AuthedUserDTO.hasRole(role: Role) = role.value in roles
fun AuthedUserDTO.hasRoles(vararg roles: String) = roles.all(this.roles::contains)
fun AuthedUserDTO.hasRoles(vararg roles: Role) = roles.map(Role::value).all(this.roles::contains)
fun AuthedUserDTO.hasOneOfRoles(vararg roles: Role) = roles.map(Role::value).any(this.roles::contains)
fun AuthedUserDTO.hasOneOfRoles(vararg roles: String) = roles.any(this.roles::contains)
