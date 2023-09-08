package city.smartb.im.f2.user.domain.query

import city.smartb.im.f2.user.domain.model.UserDTO
import city.smartb.im.f2.user.domain.model.UserDTOBase
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport

/**
 * Get a user by email.
 * @d2 function
 * @parent [city.smartb.im.f2.user.domain.D2UserPage]
 * @order 20
 */
typealias UserGetByEmailFunction = F2Function<UserGetByEmailQuery, UserGetByEmailResult>

/**
 * @d2 query
 * @parent [UserGetByEmailFunction]
 */
@JsExport
interface UserGetByEmailQueryDTO: Query {
    /**
     * Email address of the user.
     * @example [city.smartb.im.f2.user.domain.model.UserDTO.email]
     */
    val email: String
}

/**
 * @d2 inherit
 */
data class UserGetByEmailQuery(
    override val email: String
): UserGetByEmailQueryDTO

/**
 * @d2 result
 * @parent [UserGetByEmailFunction]
 */
@JsExport
interface UserGetByEmailResultDTO: Event {
    /**
     * The user matching the email, or null if it does not exist.
     */
    val item: UserDTO?
}

/**
 * @d2 inherit
 */
data class UserGetByEmailResult(
    override val item: UserDTOBase?
): UserGetByEmailResultDTO
