package io.komune.im.f2.organization.domain.query

import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationDTO
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get an organization by Siret from the Insee Sirene API.
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
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
     * @example [io.komune.im.f2.organization.domain.model.Organization.siret]
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
@Serializable
data class OrganizationGetFromInseeResult(
    /**
     * The organization.
     */
    override val item: Organization?
): OrganizationGetFromInseeResultDTO
