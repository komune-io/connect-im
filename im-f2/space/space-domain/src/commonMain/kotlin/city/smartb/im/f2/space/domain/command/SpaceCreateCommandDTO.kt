package city.smartb.im.f2.space.domain.command

import city.smartb.im.commons.model.SpaceIdentifier
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
    val identifier: SpaceIdentifier
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
    val identifier: SpaceIdentifier
}

/**
 * @d2 inherit
 */
data class SpaceCreatedEvent(
    override val identifier: SpaceIdentifier,
): SpaceCreatedEventDTO
