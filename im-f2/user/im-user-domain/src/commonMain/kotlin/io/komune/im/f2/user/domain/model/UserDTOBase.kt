package io.komune.im.f2.user.domain.model

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.AddressDTO
import io.komune.im.commons.model.UserId
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.organization.domain.model.OrganizationRefDTO
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.privilege.domain.role.model.RoleDTO
import kotlin.js.JsExport

/**
 * Representation of a user.
 * @d2 model
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 10
 */
@JsExport
interface UserDTO {
    /**
     * Id of the user.
     */
    val id: UserId

    /**
     * Organization to which the user belongs.
     */
    val memberOf: OrganizationRefDTO?

    /**
     * Email address.
     * @example "john.deuf@komune.io"
     */
    val email: String

    /**
     * First name of the user.
     * @example "John"
     */
    val givenName: String

    /**
     * Family name of the user.
     * @example "Deuf"
     */
    val familyName: String

    /**
     * Address of the user.
     */
    val address: AddressDTO?

    /**
     * Phone number of the user.
     * @example "0612345678"
     */
    val phone: String?

    /**
     * Roles assigned to the user.
     * @example {
     *      "id": "2a384297-490c-4097-8bf0-ee5e7a233e16",
     *      "identifier": "tr_orchestrator_admin",
     *      "description": "Admin role for orchestrator organization members",
     *      "targets": [["USER", "API_KEY"]],
     *      "locale": { "en": "Admin", "fr": "Admin" },
     *      "bindings": {},
     *      "permissions": [["im_organization_read", "im_organization_write", "im_user_read", "im_user_write"]]
     *   }
     */
    val roles: List<RoleDTO>

    /**
     * Additional arbitrary attributes assigned to the user.
     * @example { "age": 42 }
     */
    val attributes: Map<String, String>

    /**
     * Specifies if the user is enabled or not.
     * @example true
     */
    val enabled: Boolean

    /**
     * Id of the user that disabled the user.
     * @example null
     */
    val disabledBy: UserId?

    /**
     * Creation date of the user, as UNIX timestamp in milliseconds.
     * @example 1656938975000
     */
    val creationDate: Long

    /**
     * Disabled date of the user, as UNIX timestamp in milliseconds.
     * @example null
     */
    val disabledDate: Long?
}

/**
 * @d2 inherit
 */
data class User(
    override val id: UserId,
    override val memberOf: OrganizationRef?,
    override val email: String,
    override val givenName: String,
    override val familyName: String,
    override val address: Address?,
    override val phone: String?,
    override val roles: List<Role>,
    override val attributes: Map<String, String>,
    override val enabled: Boolean,
    override val disabledBy: UserId?,
    override val creationDate: Long,
    override val disabledDate: Long?
): UserDTO
