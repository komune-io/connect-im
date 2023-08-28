package city.smartb.im.space.domain.features.command

import city.smartb.im.space.domain.model.SpaceId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create a new space.
 * @d2 function
 * @parent [city.smartb.im.space.domain.D2SpacePage]
 * @order 10
 */
typealias SpaceCreateFunction = F2Function<SpaceCreateCommand, SpaceCreatedEvent>

@JsExport
@JsName("SpaceCreateCommandDTO")
interface SpaceCreateCommandDTO: Command {
    val name: String
}

/**
 * @d2 command
 * @parent [SpaceCreateFunction]
 */
data class SpaceCreateCommand(
    /**
     * Official name of the space.
     * @example [city.smartb.im.space.domain.model.Space.name]
     */
    override val name: String,
): SpaceCreateCommandDTO

@JsExport
@JsName("SpaceCreatedEventDTO")
interface SpaceCreatedEventDTO: Event {
    val id: SpaceId
    val name: String
}

/**
 * @d2 event
 * @parent [SpaceCreateFunction]
 */
data class SpaceCreatedEvent(
    /**
     * Identifier of the space.
     */
    override val id: SpaceId,
    /**
     * name of the space.
     */
    override val name: String,


): SpaceCreatedEventDTO
