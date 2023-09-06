package city.smartb.im.apikey.domain.features.query

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyDTO
import city.smartb.im.commons.auth.OrganizationId
import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a page of apikeys.
 * @d2 function
 * @parent [city.smartb.im.apikey.domain.D2ApiKeyPage]
 * @order 30
 */
typealias ApiKeyPageFunction = F2Function<ApiKeyPageQuery, ApiKeyPageResult>

/**
 * TODO Use PageQueryDTO and sub filter object
 */
@JsExport
@JsName("ApiKeyPageQueryDTO")
interface ApiKeyPageQueryDTO: Query {
	val search: String?
	val organizationId: OrganizationId?
	val role: String?
	val attributes: Map<String, String>?
	val withDisabled: Boolean?
	val limit: Int?
	val offset: Int?
}

/**
 * @d2 query
 * @parent [ApiKeyPageFunction]
 */
data class ApiKeyPageQuery(
	/**
	 * Search string filtering on the name of the apikey.
	 * @example "SmartB"
	 */
	override val search: String? = null,

	override val organizationId: OrganizationId? = null,

	/**
	 * Role filter.
	 */
	override val role: String? = null,

	/**
	 * Arbitrary attributes filter.
	 */
	override val attributes: Map<String, String>? = null,

	/**
	 * If false, filter out the disabled apikeys. (default: false)
	 * @example false
	 */
	override val withDisabled: Boolean? = false,

	/**
	 * Number of apikeys.
	 * @example 10
	 */
	override val limit: Int? = 10,

	/**
	 * Index of the first apikey.
	 * @example 0
	 */
	override val offset: Int? = 0
): ApiKeyPageQueryDTO

@JsExport
@JsName("ApiKeyPageResultDTO")
interface ApiKeyPageResultDTO: PageDTO<ApiKeyDTO>

/**
 * @d2 result
 * @parent [ApiKeyPageFunction]
 */
data class ApiKeyPageResult(
    /**
	 * List of apikeys satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	override val items: List<ApiKey>,

    /**
	 * The total amount of apikeys satisfying the requesting filters.
	 * @example 38
	 */
	override val total: Int
): ApiKeyPageResultDTO
