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
fun AuthedUserDTO.hasRole(role: String) = role in roles.orEmpty()
fun AuthedUserDTO.hasRole(role: ImPermission) = hasRole(role.identifier)
/** Whether the user holds every one of [roles]. */
fun AuthedUserDTO.hasRoles(vararg roles: String): Boolean = roles.all(this.roles.orEmpty()::contains)

/** Whether the user holds every one of [roles]. */
fun AuthedUserDTO.hasRoles(vararg roles: ImPermission): Boolean =
    roles.map(ImPermission::identifier).all(this.roles.orEmpty()::contains)

/** Whether the user holds at least one of [roles]. */
fun AuthedUserDTO.hasOneOfRoles(vararg roles: String): Boolean = roles.any(this.roles.orEmpty()::contains)

/** Whether the user holds at least one of [roles]. */
fun AuthedUserDTO.hasOneOfRoles(vararg roles: ImPermission): Boolean =
    roles.map(ImPermission::identifier).any(this.roles.orEmpty()::contains)
