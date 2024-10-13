package io.komune.im.f2.organization.domain.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.OrganizationId
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Get an organization by ID.
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
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
@Serializable
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
@Serializable
data class OrganizationGetResult(
    override val item: Organization?
): OrganizationGetResultDTO
