package city.smartb.im.user.domain.features.query

import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Get a user by ID.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 10
 */
typealias UserGetFunction = F2Function<UserGetQuery, UserGetResult>

typealias KeycloakUserGetFunction = i2.keycloak.f2.user.domain.features.query.UserGetFunction
typealias KeycloakUserGetQuery = i2.keycloak.f2.user.domain.features.query.UserGetQuery

/**
 * @d2 query
 * @parent [UserGetFunction]
 */
data class UserGetQuery(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Command

/**
 * @d2 result
 * @parent [UserGetFunction]
 */
data class UserGetResult(
    /**
     * The user.
     */
	val item: User?
): Event
