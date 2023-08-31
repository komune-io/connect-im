package city.smartb.im.organization.domain.features.query

import city.smartb.im.organization.domain.model.OrganizationDTO
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get an organization by ID.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 10
 */
typealias OrganizationGetFunction<MODEL> = F2Function<OrganizationGetQuery, OrganizationGetResult<MODEL>>

@JsExport
@JsName("OrganizationGetQueryDTO")
interface OrganizationGetQueryDTO: Query {
    val id: OrganizationId
}

/**
 * @d2 query
 * @parent [OrganizationGetFunction]
 */
data class OrganizationGetQuery(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId
): OrganizationGetQueryDTO

@JsExport
@JsName("OrganizationGetResultDTO")
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
