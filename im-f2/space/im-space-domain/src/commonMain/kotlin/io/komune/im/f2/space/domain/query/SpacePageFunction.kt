package io.komune.im.f2.space.domain.query

import io.komune.im.f2.space.domain.model.Space
import io.komune.im.f2.space.domain.model.SpaceDTO
import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get a page of spaces.
 * @d2 function
 * @parent [io.komune.im.space.domain.D2SpacePage]
 * @order 30
 */
typealias SpacePageFunction = F2Function<SpacePageQuery, SpacePageResult>

/**
 * TODO Use PageQueryDTO and sub filter object
 */
@JsExport
@JsName("SpacePageQueryDTO")
interface SpacePageQueryDTO: Query {
	val search: String?
	val page: Int?
	val size: Int?
}

/**
 * @d2 query
 * @parent [SpacePageFunction]
 */
@Serializable
data class SpacePageQuery(
	/**
	 * Search string filtering on the name of the space.
	 * @example "Komune"
	 */
	override val search: String? = null,
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
): SpacePageQueryDTO

@JsExport
@JsName("SpacePageResultDTO")
interface SpacePageResultDTO: PageDTO<SpaceDTO>

/**
 * @d2 result
 * @parent [SpacePageFunction]
 */
@Serializable
data class SpacePageResult(
    /**
	 * List of spaces satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	override val items: List<Space>,
    /**
     * Represents pagination information for a page.
     */
    val pagination: PagePagination?,
    /**
	 * The total amount of users satisfying the requesting filters.
	 * @example 38
	 */
	override val total: Int
): SpacePageResultDTO
