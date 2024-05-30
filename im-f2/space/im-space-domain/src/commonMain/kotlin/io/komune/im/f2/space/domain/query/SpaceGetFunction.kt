package io.komune.im.f2.space.domain.query

import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.f2.space.domain.model.Space
import io.komune.im.f2.space.domain.model.SpaceDTO
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get a space by ID.
 * @d2 function
 * @parent [io.komune.im.space.domain.D2SpacePage]
 * @order 10
 */
typealias SpaceGetFunction = F2Function<SpaceGetQuery, SpaceGetResult>

@JsExport
@JsName("SpaceGetQueryDTO")
interface SpaceGetQueryDTO: Query {
    val id: SpaceIdentifier
}

/**
 * @d2 query
 * @parent [SpaceGetFunction]
 */
@Serializable
data class SpaceGetQuery(
    /**
     * Identifier of the space.
     */
    override val id: SpaceIdentifier
): SpaceGetQueryDTO

@JsExport
@JsName("SpaceGetResultDTO")
interface SpaceGetResultDTO: Event {
    val item: SpaceDTO?
}

/**
 * @d2 result
 * @parent [SpaceGetFunction]
 */
@Serializable
data class SpaceGetResult(
    /**
     * The space.
     */
    override val item: Space?
): SpaceGetResultDTO
