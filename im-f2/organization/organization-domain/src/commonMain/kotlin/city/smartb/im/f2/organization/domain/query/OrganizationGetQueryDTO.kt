package city.smartb.im.f2.organization.domain.query

import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.organization.domain.model.OrganizationDTO
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport

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
@JsExport
interface OrganizationGetQueryDTO: Query {
    /**
     * Id of the organization to get.
     */
    val id: OrganizationId
}

/**
 * @d2 inherit
 */
data class OrganizationGetQuery(
    override val id: OrganizationId
): OrganizationGetQueryDTO

/**
 * @d2 result
 * @parent [OrganizationGetFunction]
 */
@JsExport
interface OrganizationGetResultDTO: Event {
    /**
     * The organization matching the given id, or null if it does not exist.
     */
    val item: OrganizationDTO?
}

/**
 * @d2 inherit
 */
data class OrganizationGetResult(
    override val item: OrganizationDTOBase?
): OrganizationGetResultDTO
