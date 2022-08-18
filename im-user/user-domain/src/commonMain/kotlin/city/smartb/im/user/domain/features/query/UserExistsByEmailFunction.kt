package city.smartb.im.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Check if a user exists by email.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 21
 */
typealias UserExistsByEmailFunction = F2Function<UserExistsByEmailQuery, UserExistsByEmailResult>

@JsExport
@JsName("UserExistsByEmailQueryDTO")
interface UserExistsByEmailQueryDTO: Query {
    /**
     * Email address of the user.
     */
    val email: String
}

/**
 * @d2 query
 * @parent [UserExistsByEmailFunction]
 */
data class UserExistsByEmailQuery(
    override val email: String
): UserExistsByEmailQueryDTO

@JsExport
@JsName("UserExistsByEmailResultDTO")
interface UserExistsByEmailResultDTO: Event {
    /**
     * True if the user exists, false else.
     */
    val item: Boolean
}

/**
 * @d2 result
 * @parent [UserExistsByEmailFunction]
 */
data class UserExistsByEmailResult(
    override val item: Boolean
): UserExistsByEmailResultDTO
