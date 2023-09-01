package city.smartb.im.organization.domain.features.query

import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationDTO
import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a page of organizations.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 30
 */
typealias OrganizationPageFunction = F2Function<OrganizationPageQuery, OrganizationPageResult>

@JsExport
@JsName("OrganizationPageQueryDTO")
interface OrganizationPageQueryDTO: Query {
	val search: String?
	val role: String?
	val roles: List<String>?
	val attributes: Map<String, String>?
	val withDisabled: Boolean?
	val page: Int?
	val size: Int?
}

/**
 * @d2 query
 * @parent [OrganizationPageFunction]
 */
data class OrganizationPageQuery(
	/**
	 * Search string filtering on the name of the organization.
	 * @example "SmartB"
	 */
	override val search: String? = null,

	/**
	 * Role filter.
	 */
	override val role: String? = null,

	/**
	 * Arbitrary attributes filter.
	 */
	override val attributes: Map<String, String>? = null,

    /**
	 * If false, filter out the disabled organizations. (default: false)
	 * @example false
	 */
	override val withDisabled: Boolean? = false,

    /**
	 * Number of the page.
	 * @example 0
	 */
	override val page: Int? = null,

	/**
	 * Size of the page.
	 * @example 10
	 */
	override val size: Int? = null,

	/**
	 * Role filter.
	 */
	override val roles: List<String>? = null
): OrganizationPageQueryDTO

/**
 * @d2 result
 * @parent [OrganizationPageFunction]
 */
@JsExport
@JsName("OrganizationPageResultDTO")
interface OrganizationPageResultDTO: PageDTO<OrganizationDTO>

/**
 * @d2 inherit
 */
data class OrganizationPageResult(
	/**
	 * List of organizations satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	override val items: List<Organization>,

	/**
	 * The total amount of organization satisfying the requesting filters.
	 * @example 38
	 */
	override val total: Int
): OrganizationPageResultDTO
