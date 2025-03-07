package io.komune.im.commons.auth

import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("AuthedUserDTO")
interface AuthedUserDTO {
    val id: UserId
    val identifier: String?
    val memberOf: OrganizationId?
    val roles: Array<String>?

    /**
     * Authentication Context Class Reference
     */
    val acr: String?
}

data class AuthedUser(
    override val id: UserId,
    override val identifier: String?,
    override val memberOf: OrganizationId?,
    override val roles: Array<String>?,
    override val acr: String?
) : AuthedUserDTO

fun AuthedUserDTO.hasAcr(acrValue: String) = acr == acrValue
fun AuthedUserDTO.hasRole(role: String) = role in (roles ?: emptyArray())
fun AuthedUserDTO.hasRole(role: ImPermission) = hasRole(role.identifier)
fun AuthedUserDTO.hasRoles(vararg roles: String): Boolean {
    val myRoles = this.roles ?: emptyArray()
    return roles.all(myRoles::contains)
}

fun AuthedUserDTO.hasRoles(vararg roles: ImPermission) =
    hasRoles(*roles.map(ImPermission::identifier).toTypedArray())

fun AuthedUserDTO.hasOneOfRoles(vararg roles: String): Boolean {
    val myRoles = this.roles ?: emptyArray()
    return roles.any(myRoles::contains)
}

fun AuthedUserDTO.hasOneOfRoles(vararg roles: ImPermission) =
    hasOneOfRoles(*roles.map(ImPermission::identifier).toTypedArray())
