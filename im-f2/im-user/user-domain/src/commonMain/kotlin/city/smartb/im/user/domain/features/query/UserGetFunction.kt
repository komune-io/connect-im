package city.smartb.im.user.domain.features.query

import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserDTO
import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a user by ID.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 10
 */
typealias UserGetFunction = F2Function<UserGetQuery, UserGetResult>

typealias KeycloakUserGetFunction = i2.keycloak.f2.user.domain.features.query.UserGetFunction
typealias KeycloakUserGetQuery = i2.keycloak.f2.user.domain.features.query.UserGetQuery

@JsExport
@JsName("UserGetQueryDTO")
interface UserGetQueryDTO: Query {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 query
 * @parent [UserGetFunction]
 */
data class UserGetQuery(
    override val id: UserId
): UserGetQueryDTO

@JsExport
@JsName("UserGetResultDTO")
interface UserGetResultDTO: Event {
    /**
     * The user.
     */
    val item: UserDTO?
}

/**
 * @d2 result
 * @parent [UserGetFunction]
 */
data class UserGetResult(
    override val item: User?
): UserGetResultDTO
