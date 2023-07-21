package city.smartb.im.apikey.domain.features.query

import city.smartb.im.apikey.domain.model.ApiKeyDTO
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
typealias ApiKeyPageFunction<MODEL> = F2Function<ApiKeyPageQuery, ApiKeyPageResult<MODEL>>

/**
 * TODO Use PageQueryDTO and sub filter object
 */
@JsExport
@JsName("ApiKeyPageQueryDTO")
interface ApiKeyPageQueryDTO: Query {
	val search: String?
	val role: String?
	val attributes: Map<String, String>?
	val withDisabled: Boolean?
	val page: Int?
	val size: Int?
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
	override val search: String?,

	/**
	 * Role filter.
	 */
	override val role: String?,

	/**
	 * Arbitrary attributes filter.
	 */
	override val attributes: Map<String, String>?,

	/**
	 * If false, filter out the disabled apikeys. (default: false)
	 * @example false
	 */
	override val withDisabled: Boolean? = false,

	/**
	 * Number of the page.
	 * @example 0
	 */
	override val page: Int?,

	/**
	 * Size of the page.
	 * @example 10
	 */
	override val size: Int?
): ApiKeyPageQueryDTO

@JsExport
@JsName("ApiKeyPageResultDTO")
interface ApiKeyPageResultDTO<MODEL: ApiKeyDTO>: PageDTO<MODEL>

/**
 * @d2 result
 * @parent [ApiKeyPageFunction]
 */
data class ApiKeyPageResult<MODEL: ApiKeyDTO>(
	/**
	 * List of apikeys satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	override val items: List<MODEL>,

	/**
	 * The total amount of users satisfying the requesting filters.
	 * @example 38
	 */
	override val total: Int
): ApiKeyPageResultDTO<MODEL>
