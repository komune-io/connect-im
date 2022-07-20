package city.smartb.im.user.domain.features.query

import city.smartb.im.user.domain.model.User
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Get a user by email.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 20
 */
typealias UserGetByEmailFunction = F2Function<UserGetByEmailQuery, UserGetByEmailResult>

typealias KeycloakUserGetByEmailFunction = i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
typealias KeycloakUserGetByEmailQuery = i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery

/**
 * @d2 query
 * @parent [UserGetByEmailFunction]
 */
data class UserGetByEmailQuery(
    /**
     * Email address of the user.
     */
    val email: String
): Command

/**
 * @d2 result
 * @parent [UserGetByEmailFunction]
 */
data class UserGetByEmailResult(
    /**
     * The user.
     */
	val item: User?
): Event
