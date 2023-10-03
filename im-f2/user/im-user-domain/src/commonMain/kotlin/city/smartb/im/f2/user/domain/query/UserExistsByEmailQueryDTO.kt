package city.smartb.im.f2.user.domain.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport

/**
 * Check if a user exists by email.
 * @d2 function
 * @parent [city.smartb.im.f2.user.domain.D2UserPage]
 * @order 21
 */
typealias UserExistsByEmailFunction = F2Function<UserExistsByEmailQuery, UserExistsByEmailResult>

/**
 * @d2 query
 * @parent [UserExistsByEmailFunction]
 */
@JsExport
interface UserExistsByEmailQueryDTO: Query {
    /**
     * Email address of the user.
     * @example [city.smartb.im.f2.user.domain.model.UserDTO.email]
     */
    val email: String
}

/**
 * @d2 inherit
 */
data class UserExistsByEmailQuery(
    override val email: String
): UserExistsByEmailQueryDTO

/**
 * @d2 result
 * @parent [UserExistsByEmailFunction]
 */
@JsExport
interface UserExistsByEmailResultDTO: Event {
    /**
     * True if the user exists, false else.
     * @example true
     */
    val item: Boolean
}

/**
 * @d2 inherit
 */
data class UserExistsByEmailResult(
    override val item: Boolean
): UserExistsByEmailResultDTO
