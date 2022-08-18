package city.smartb.im.user.domain.features.query

import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserDTO
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a page of users.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 30
 */
typealias UserPageFunction = F2Function<UserPageQuery, UserPageResult>

typealias KeycloakUserPageQuery = i2.keycloak.f2.user.domain.features.query.UserPageQuery
typealias KeycloakUserPageFunction = i2.keycloak.f2.user.domain.features.query.UserPageFunction

@JsExport
@JsName("UserPageQueryDTO")
interface UserPageQueryDTO: Query {
	/**
	 * Organization ID filter.
	 */
	val organizationId: OrganizationId?

	/**
	 * Search string filtering on the email, firstname and lastname of the user.
	 */
	val search: String?

	/**
	 * Role filter.
	 */
	val role: String?

	/**
	 * Arbitrary attributes filter.
	 */
	val attributes: Map<String, String>?

	/**
	 * If false, filter out the disabled users. (default: false)
	 * @example false
	 */
	val withDisabled: Boolean

	/**
	 * Number of the page.
	 * @example 0
	 */
	val page: Int?

	/**
	 * Size of the page.
	 * @example 10
	 */
	val size: Int?
}

/**
 * @d2 query
 * @parent [UserPageFunction]
 */
data class UserPageQuery(
	override val organizationId: OrganizationId?,
	override val search: String?,
	override val role: String?,
	override val attributes: Map<String, String>?,
	override val withDisabled: Boolean,
	override val page: Int?,
	override val size: Int?
): UserPageQueryDTO

@JsExport
@JsName("UserPageResultDTO")
interface UserPageResultDTO: Event {
	/**
	 * List of users satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	val items: List<UserDTO>

	/**
	 * The total amount of users satisfying the requesting filters.
	 * @example 38
	 */
	val total: Int
}

/**
 * @d2 result
 * @parent [UserPageFunction]
 */
data class UserPageResult(
	override val items: List<User>,
	override val total: Int
): UserPageResultDTO
