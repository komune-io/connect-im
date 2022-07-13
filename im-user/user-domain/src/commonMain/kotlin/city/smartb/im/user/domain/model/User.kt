package city.smartb.im.user.domain.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressBase
import city.smartb.im.organization.domain.model.OrganizationRef
import city.smartb.im.organization.domain.model.OrganizationRefDTO
import i2.keycloak.f2.user.domain.model.UserRoles

/**
 * Unique identifier of the user.
 * @d2 model
 * @parent [city.smartb.im.user.domain.D2UserModelSection]
 * @order 20
 * @visual json "e8322a0b-b4cf-4643-a398-c442d22504be"
 */
typealias UserId = String

/**
 * Representation of the user.
 * @d2 model
 * @parent [city.smartb.im.user.domain.D2UserModelSection]
 */
interface User {
    /**
     * Identifier of the user.
     */
    val id: UserId

    /**
     * Organization Ref to which the user belongs.
     */
    val memberOf: OrganizationRefDTO?

    /**
     * Email address.
     * @example "user@smartb.city"
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
    val address: Address?

    /**
     * Telephone number of the user.
     * @example "06 12 34 56 78"
     */
    val phone: String?

    /**
     * Roles of the user.
     */
    val roles: UserRoles

    /**
     * Platform-specific attributes assigned to the user
     * @example { "age": "42" }
     */
    val attributes: Map<String, String>

    /**
     * Send a validation email to the user on subscription.
     * @example "true"
     */
    val sendEmailLink: Boolean?

    /**
     * Creation date of the user, as UNIX timestamp in milliseconds.
     * @example 1656938975000
     */
    val creationDate: Long
}

data class UserBase(
    override val id: UserId,
    override val memberOf: OrganizationRef?,
    override val email: String,
    override val givenName: String,
    override val familyName: String,
    override val address: AddressBase?,
    override val phone: String?,
    override val roles: UserRoles,
    override val attributes: Map<String, String>,
    override val sendEmailLink: Boolean? = true,
    override val creationDate: Long
): User
