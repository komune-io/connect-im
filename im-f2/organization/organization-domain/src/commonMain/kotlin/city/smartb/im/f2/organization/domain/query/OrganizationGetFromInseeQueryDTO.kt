package city.smartb.im.f2.organization.domain.query

import city.smartb.im.f2.organization.domain.model.OrganizationDTO
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get an organization by Siret from the Insee Sirene API.
 * @d2 function
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @order 20
 */
typealias OrganizationGetFromInseeFunction =
        F2Function<OrganizationGetFromInseeQuery, OrganizationGetFromInseeResult>

@JsExport
@JsName("OrganizationGetFromInseeQueryDTO")
interface OrganizationGetFromInseeQueryDTO: Query {
    val siret: String
}

/**
 * @d2 query
 * @parent [OrganizationGetFromInseeFunction]
 */
data class OrganizationGetFromInseeQuery(
    /**
     * Siret number of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.siret]
     */
    override val siret: String
): OrganizationGetFromInseeQueryDTO

@JsExport
@JsName("OrganizationGetFromInseeResultDTO")
interface OrganizationGetFromInseeResultDTO: Event {
    val item: OrganizationDTO?
}

/**
 * @d2 result
 * @parent [OrganizationGetFromInseeFunction]
 */
data class OrganizationGetFromInseeResult(
    /**
     * The organization.
     */
    override val item: OrganizationDTOBase?
): OrganizationGetFromInseeResultDTO
