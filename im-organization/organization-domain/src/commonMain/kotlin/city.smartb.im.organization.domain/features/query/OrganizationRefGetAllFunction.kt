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
typealias OrganizationRefGetAllFunction = F2Function<OrganizationRefGetAllQuery, OrganizationRefGetAllResult>

/**
 * @d2 query
 * @parent [OrganizationRefGetAllFunction]
 */
class OrganizationRefGetAllQuery: Command

/**
 * @d2 result
 * @parent [OrganizationRefGetAllFunction]
 */
data class OrganizationRefGetAllResult(
	/**
	 * All Organization Refs.
	 */
	val items: List<OrganizationRef>
): Event
