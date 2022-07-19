package city.smartb.im.organization.domain.features.query

import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Get an organization by ID.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 10
 */
typealias OrganizationGetFunction = F2Function<OrganizationGetQuery, OrganizationGetResult>

/**
 * @d2 query
 * @parent [OrganizationGetFunction]
 */
data class OrganizationGetQuery(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId
): Command

/**
 * @d2 result
 * @parent [OrganizationGetFunction]
 */
data class OrganizationGetResult(
    /**
     * The organization.
     */
	val item: Organization?
): Event
