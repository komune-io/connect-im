package city.smartb.im.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import city.smartb.im.organization.domain.model.OrganizationRef

/**
 * Gets all organization refs.
 * @d2 section
 * @parent [city.smartb.im.organization.domain.D2OrganizationRefQuerySection]
 */
typealias OrganizationRefGetAllFunction = F2Function<OrganizationRefGetAllQuery, OrganizationRefGetAllResult>

/**
 * @d2 query
 * @parent [OrganizationRefGetAllFunction]
 */
class OrganizationRefGetAllQuery: Command

/**
 * @d2 event
 * @parent [OrganizationRefGetAllFunction]
 */
data class OrganizationRefGetAllResult(
	/**
	 * All Organization Refs.
	 */
	val items: List<OrganizationRef>
): Event
