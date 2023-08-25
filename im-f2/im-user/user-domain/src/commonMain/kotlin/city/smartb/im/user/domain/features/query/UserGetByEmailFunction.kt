package city.smartb.im.user.domain.features.query

import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserDTO
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a user by email.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 20
 */
typealias UserGetByEmailFunction = F2Function<UserGetByEmailQuery, UserGetByEmailResult>

typealias KeycloakUserGetByEmailFunction = i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
typealias KeycloakUserGetByEmailQuery = i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery

@JsExport
@JsName("UserGetByEmailQueryDTO")
interface UserGetByEmailQueryDTO: Query {
    /**
     * Email address of the user.
     */
    val email: String
}

/**
 * @d2 query
 * @parent [UserGetByEmailFunction]
 */
data class UserGetByEmailQuery(
    override val email: String
): UserGetByEmailQueryDTO

@JsExport
@JsName("UserGetByEmailResultDTO")
interface UserGetByEmailResultDTO: Event {
    /**
     * The user.
     */
    val item: UserDTO?
}

/**
 * @d2 result
 * @parent [UserGetByEmailFunction]
 */
data class UserGetByEmailResult(
    override val item: User?
): UserGetByEmailResultDTO
