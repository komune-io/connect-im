package io.komune.im.f2.organization.domain.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.OrganizationId
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.organization.domain.model.OrganizationRefDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Get an organization ref by ID.
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 * @order 10
 */
typealias OrganizationRefGetFunction = F2Function<OrganizationRefGetQuery, OrganizationRefGetResult>

/**
 * @d2 query
 * @parent [OrganizationRefGetFunction]
 */
@JsExport
interface OrganizationRefGetQueryDTO: Query {
    /**
     * Id of the organization ref to get.
     */
    val id: OrganizationId
}

/**
 * @d2 inherit
 */
@Serializable
data class OrganizationRefGetQuery(
    override val id: OrganizationId
): OrganizationRefGetQueryDTO

/**
 * @d2 result
 * @parent [OrganizationRefGetFunction]
 */
@JsExport
interface OrganizationRefGetResultDTO: Event {
    /**
     * The organization ref matching the given id, or null if it does not exist.
     */
    val item: OrganizationRefDTO?
}

/**
 * @d2 inherit
 */
@Serializable
data class OrganizationRefGetResult(
    override val item: OrganizationRef?
): OrganizationRefGetResultDTO
