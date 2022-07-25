package city.smartb.im.organization.domain.features.query

import city.smartb.im.organization.domain.model.OrganizationDTO
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
typealias OrganizationGetFunction<MODEL> = F2Function<OrganizationGetQuery, OrganizationGetResult<MODEL>>

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


interface OrganizationGetResultDTO<out MODEL: OrganizationDTO>: Event {
    val item: MODEL?
}

/**
 * @d2 result
 * @parent [OrganizationGetFunction]
 */
data class OrganizationGetResult<out MODEL: OrganizationDTO>(
    /**
     * The organization.
     */
    override val item: MODEL?
): OrganizationGetResultDTO<MODEL>
