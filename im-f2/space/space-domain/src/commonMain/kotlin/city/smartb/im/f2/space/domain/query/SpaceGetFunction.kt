package city.smartb.im.f2.space.domain.query

import city.smartb.im.commons.model.SpaceIdentifier
import city.smartb.im.f2.space.domain.model.Space
import city.smartb.im.f2.space.domain.model.SpaceDTO
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a space by ID.
 * @d2 function
 * @parent [city.smartb.im.space.domain.D2SpacePage]
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
data class SpaceGetResult(
    /**
     * The space.
     */
    override val item: Space?
): SpaceGetResultDTO
