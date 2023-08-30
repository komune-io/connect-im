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

/**
 * @d2 command
 * @parent [SpaceCreateFunction]
 */
@JsExport
@JsName("SpaceCreateCommandDTO")
interface SpaceCreateCommandDTO: Command {
    /**
     * Identifier of the space to create.
     */
    val identifier: SpaceId
}

/**
 * @d2 inherit
 */
data class SpaceCreateCommand(
    override val identifier: String
): SpaceCreateCommandDTO

/**
 * @d2 event
 * @parent [SpaceCreateFunction]
 */
@JsExport
@JsName("SpaceCreatedEventDTO")
interface SpaceCreatedEventDTO: Event {
    /**
     * Identifier of the created space.
     */
    val identifier: SpaceId
}

/**
 * @d2 inherit
 */
data class SpaceCreatedEvent(
    override val identifier: SpaceId,
): SpaceCreatedEventDTO
