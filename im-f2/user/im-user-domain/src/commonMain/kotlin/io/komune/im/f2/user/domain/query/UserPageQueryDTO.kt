package io.komune.im.f2.user.domain.query

import io.komune.im.commons.model.OrganizationId
import io.komune.im.f2.user.domain.model.User
import io.komune.im.f2.user.domain.model.UserDTO
import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a page of users.
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @order 30
 */
typealias UserPageFunction = F2Function<UserPageQuery, UserPageResult>

/**
 * @d2 query
 * @parent [UserPageFunction]
 */
@JsExport
@JsName("UserPageQueryDTO")
interface UserPageQueryDTO: Query {
	/**
	 * Filter on user organization id.
     * @default null
     */
	val organizationId: OrganizationId?

    /**
     * Filter on user organization name.
     * @default null
     */
    val organizationName: String?

	/**
	 * Search string filtering on the firstname and lastname of the user. Case insensitive.
     * @example "john"
     * @default null
     */
	val name: String?

	/**
	 * Search string filtering on the email of the user. Case insensitive.
     * @example "john"
     * @default null
     */
	val email: String?

	/**
	 * Filter on user roles.
     * @default null
     */
	val role: String?

	/**
	 * Filter on user roles
     * @example [["tr_orchestrator_admin"]]
     * @default null
	 */
	val roles: List<String>?

	/**
	 * Arbitrary attributes filter.
     * @example [io.komune.im.f2.user.domain.model.UserDTO.attributes]
     * @default null
	 */
	val attributes: Map<String, String>?

	/**
	 * If false, filter out the disabled users.
     * @default false
	 */
	val withDisabled: Boolean

	val offset: Int?
	val limit: Int?
}

/**
 * @d2 inherit
 */
data class UserPageQuery(
    override val organizationId: OrganizationId? = null,
    override val organizationName: String? = null,
    override val name: String? = null,
    override val email: String? = null,
    override val role: String? = null,
    override val attributes: Map<String, String>? = null,
    override val withDisabled: Boolean = false,
    override val roles: List<String>? = null,
    override val offset: Int? = null,
    override val limit: Int? = null
): UserPageQueryDTO

/**
 * @d2 result
 * @parent [UserPageFunction]
 */
@JsExport
@JsName("UserPageResultDTO")
interface UserPageResultDTO: PageDTO<UserDTO> {
    /**
     * Users matching the filters.
     */
    override val items: List<UserDTO>

    /**
     * Total count of users matching the filters
     */
    override val total: Int
}

/**
 * @d2 inherit
 */
data class UserPageResult(
    override val items: List<User>,
    override val total: Int
): UserPageResultDTO
