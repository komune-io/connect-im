package city.smartb.im.user.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Check if a user exists by email.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 21
 */
typealias UserExistsByEmailFunction = F2Function<UserExistsByEmailQuery, UserExistsByEmailResult>

/**
 * @d2 query
 * @parent [UserExistsByEmailFunction]
 */
data class UserExistsByEmailQuery(
    /**
     * Email address of the user.
     */
    val email: String
): Command

/**
 * @d2 result
 * @parent [UserExistsByEmailFunction]
 */
data class UserExistsByEmailResult(
    /**
     * True if the user exists, false else.
     */
	val item: Boolean
): Event
