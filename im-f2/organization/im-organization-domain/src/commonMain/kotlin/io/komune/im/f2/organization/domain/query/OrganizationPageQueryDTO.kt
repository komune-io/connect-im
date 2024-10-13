package io.komune.im.f2.organization.domain.query

import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.fnc.F2Function
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Get a page of organizations.
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 * @order 30
 */
typealias OrganizationPageFunction = F2Function<OrganizationPageQuery, OrganizationPageResult>

/**
 * @d2 query
 * @parent [OrganizationPageFunction]
 */
@JsExport
interface OrganizationPageQueryDTO: Query {
    /**
     * Search string filtering on the name of the organization.
     * @example "Komune"
     */
	val name: String?

    /**
	 * Role filter.
	 */
	val role: String?

    val roles: List<String>?

    /**
     * Arbitrary attributes filter.
     */
    val attributes: Map<String, String>?

    /**
     * Status filter. See [OrganizationStatus][io.komune.im.f2.organization.domain.model.OrganizationStatus]
     */
    val status: List<String>?

    /**
     * If false, filter out the disabled organizations. (default: false)
     * @example false
     */
	val withDisabled: Boolean?
    val offset: Int?
    val limit: Int?
}

/**
 * @d2 result
 * @parent [OrganizationPageFunction]
 */
@Serializable
data class OrganizationPageQuery(
	override val name: String? = null,
	override val role: String? = null,
	override val attributes: Map<String, String>? = null,
	override val status: List<String>? = null,
	override val withDisabled: Boolean? = false,
    override val offset: Int?,
    override val limit: Int?,
	override val roles: List<String>? = null
): OrganizationPageQueryDTO

/**
 * @d2 result
 * @parent [OrganizationPageFunction]
 */
@JsExport
interface OrganizationPageResultDTO: PageDTO<OrganizationDTO> {
    /**
     * List of organizations satisfying the requesting filters.
     * The size of the list is lesser or equal than the requested size.
     */
    override val items: List<OrganizationDTO>

    /**
     * The total amount of organization satisfying the requesting filters.
     * @example 38
     */
    override val total: Int
}

/**
 * @d2 inherit
 */
@Serializable
data class OrganizationPageResult(
    override val items: List<Organization>,
    override val total: Int
): OrganizationPageResultDTO
