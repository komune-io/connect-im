package city.smartb.im.organization.domain.features.query

import city.smartb.im.organization.domain.model.OrganizationRef
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Get all organization refs.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 40
 */
typealias OrganizationRefListFunction = F2Function<OrganizationRefListQuery, OrganizationRefListResult>

/**
 * @d2 query
 * @parent [OrganizationRefListFunction]
 */
data class OrganizationRefListQuery(
	/**
	 * If false, filter out the disabled organizations. (default: false)
	 * @example false
	 */
	val withDisabled: Boolean = false,
): Command

/**
 * @d2 result
 * @parent [OrganizationRefListFunction]
 */
data class OrganizationRefListResult(
	/**
	 * All Organization Refs.
	 */
	val items: List<OrganizationRef>
): Event
