package city.smartb.im.user.domain.features.query

import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.domain.model.User
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Get a page of users.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 30
 */
typealias UserPageFunction = F2Function<UserPageQuery, UserPageResult>

typealias KeycloakUserPageQuery = i2.keycloak.f2.user.domain.features.query.UserPageQuery
typealias KeycloakUserPageFunction = i2.keycloak.f2.user.domain.features.query.UserPageFunction

/**
 * @d2 query
 * @parent [UserPageFunction]
 */
data class UserPageQuery(
	/**
	 * Organization ID filter.
	 */
	val organizationId: OrganizationId?,

	/**
	 * Search string filtering on the email, firstname and lastname of the user.
	 */
	val search: String?,

	/**
	 * Role filter.
	 */
	val role: String?,

	/**
	 * Arbitrary attributes filter.
	 */
	val attributes: Map<String, String>?,

	/**
	 * Number of the page.
	 * @example 0
	 */
	val page: Int?,

	/**
	 * Size of the page.
	 * @example 10
	 */
	val size: Int?
): Command

/**
 * @d2 result
 * @parent [UserPageFunction]
 */
data class UserPageResult(
	/**
	 * List of users satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	val items: List<User>,

	/**
	 * The total amount of users satisfying the requesting filters.
	 * @example 38
	 */
	val total: Int
): Event
