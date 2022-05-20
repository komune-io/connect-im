package city.smartb.im.user.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import city.smartb.im.user.domain.model.UserBase
import city.smartb.im.user.domain.model.UserId

/**
 * Gets a user by ID.
 * @d2 section
 * @parent [city.smartb.im.user.domain.D2UserQuerySection]
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
 * @d2 event
 * @parent [UserGetFunction]
 */
data class UserGetResult(
    /**
     * The user.
     */
	val item: UserBase?
): Event
