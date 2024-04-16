package io.komune.im.apikey.domain.query

import io.komune.im.apikey.domain.model.ApiKey
import io.komune.im.apikey.domain.model.ApiKeyDTO
import io.komune.im.commons.model.OrganizationId
import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a page of apikeys.
 * @d2 function
 * @parent [io.komune.im.apikey.domain.D2ApiKeyPage]
 * @order 30
 */
typealias ApiKeyPageFunction = F2Function<ApiKeyPageQuery, ApiKeyPageResult>

/**
 * @d2 query
 * @parent [ApiKeyPageFunction]
 */
@JsExport
@JsName("ApiKeyPageQueryDTO")
interface ApiKeyPageQueryDTO: Query {
    /**
     * Search string filtering on the name of the apikey.
     * @example "Komune"
     */
	val search: String?
	val organizationId: OrganizationId?

    /**
     * Role filter.
     */
	val role: String?

    /**
     * Arbitrary attributes filter.
     */
	val attributes: Map<String, String>?

    /**
     * If false, filter out the disabled apikeys.
     * @example false
     * @default false
     */
	val withDisabled: Boolean?

    /**
     * Maximum number of apikeys to fetch.
     * @example 10
     * @default 10
     */
	val limit: Int?

    /**
     * Index of the first apikey.
     * @example 0
     * @default 0
     */
	val offset: Int?
}

/**
 * @d2 inherit
 */
data class ApiKeyPageQuery(
	override val search: String? = null,
	override val organizationId: OrganizationId? = null,
	override val role: String? = null,
	override val attributes: Map<String, String>? = null,
	override val withDisabled: Boolean? = false,
	override val limit: Int? = 10,
	override val offset: Int? = 0
): ApiKeyPageQueryDTO

/**
 * @d2 result
 * @parent [ApiKeyPageFunction]
 */
@JsExport
@JsName("ApiKeyPageResultDTO")
interface ApiKeyPageResultDTO: PageDTO<ApiKeyDTO> {
    /**
     * List of apikeys satisfying the requesting filters. The size of the list is lesser or equal than the requested limit.
     */
    override val items: List<ApiKeyDTO>

    /**
     * The total amount of apikeys satisfying the requesting filters.
     * @example 38
     */
    override val total: Int
}

/**
 * @d2 inherit
 */
data class ApiKeyPageResult(
    override val items: List<ApiKey>,
    override val total: Int
): ApiKeyPageResultDTO
