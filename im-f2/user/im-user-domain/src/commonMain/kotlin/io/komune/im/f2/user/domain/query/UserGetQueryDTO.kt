package io.komune.im.f2.user.domain.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.UserId
import io.komune.im.f2.user.domain.model.User
import io.komune.im.f2.user.domain.model.UserDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Get a user by id.
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 10
 */
typealias UserGetFunction = F2Function<UserGetQuery, UserGetResult>

/**
 * @d2 query
 * @parent [UserGetFunction]
 */
@JsExport
interface UserGetQueryDTO: Query {
    /**
     * Id of the user.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserGetQuery(
    override val id: UserId
): UserGetQueryDTO

/**
 * @d2 result
 * @parent [UserGetFunction]
 */
@JsExport
interface UserGetResultDTO: Event {
    /**
     * The user matching the given id, or null if not does not exist.
     */
    val item: UserDTO?
}

/**
 * @d2 inherit
 */
@Serializable
data class UserGetResult(
    override val item: User?
): UserGetResultDTO
