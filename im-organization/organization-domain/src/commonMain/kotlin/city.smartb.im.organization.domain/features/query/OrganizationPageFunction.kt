package city.smartb.im.organization.domain.features.query

import city.smartb.im.organization.domain.model.Organization
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Get a page of organizations.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 30
 */
typealias OrganizationPageFunction = F2Function<OrganizationPageQuery, OrganizationPageResult>

/**
 * @d2 query
 * @parent [OrganizationPageFunction]
 */
data class OrganizationPageQuery(
	/**
	 * Search string filtering on the name of the organization.
	 * @example "SmartB"
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
	 * If false, filter out the disabled organizations. (default: false)
	 * @example false
	 */
	val withDisabled: Boolean = false,

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
 * @parent [OrganizationPageFunction]
 */
data class OrganizationPageResult(
	/**
	 * List of organizations satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	val items: List<Organization>,

	/**
	 * The total amount of users satisfying the requesting filters.
	 * @example 38
	 */
	val total: Int
): Event
