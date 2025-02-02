package io.komune.im.f2.organization.domain.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.organization.domain.model.OrganizationRefDTO
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get all organization refs.
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 * @order 40
 */
typealias OrganizationRefListFunction = F2Function<OrganizationRefListQuery, OrganizationRefListResult>

@JsExport
@JsName("OrganizationRefListQueryDTO")
interface OrganizationRefListQueryDTO: Query {
	val withDisabled: Boolean
}

/**
 * @d2 query
 * @parent [OrganizationRefListFunction]
 */
@Serializable
data class OrganizationRefListQuery(
	/**
	 * If false, filter out the disabled organizations. (default: false)
	 * @example false
	 */
	override val withDisabled: Boolean = false,
): OrganizationRefListQueryDTO

@JsExport
@JsName("OrganizationRefListResultDTO")
interface OrganizationRefListResultDTO: Event {
	val items: List<OrganizationRefDTO>
}

/**
 * @d2 result
 * @parent [OrganizationRefListFunction]
 */
@Serializable
data class OrganizationRefListResult(
	/**
	 * All Organization Refs.
	 */
	override val items: List<OrganizationRef>
): OrganizationRefListResultDTO
