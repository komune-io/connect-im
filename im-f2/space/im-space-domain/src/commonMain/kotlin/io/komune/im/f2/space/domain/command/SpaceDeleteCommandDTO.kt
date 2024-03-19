package io.komune.im.f2.space.domain.command

import io.komune.im.commons.model.SpaceIdentifier
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Delete a space (but not its users).
 * @d2 function
 * @parent [io.komune.im.space.domain.D2SpacePage]
 * @order 50
 */
typealias SpaceDeleteFunction = F2Function<SpaceDeleteCommand, SpaceDeletedEvent>

@JsExport
@JsName("SpaceDeleteCommandDTO")
interface SpaceDeleteCommandDTO: Command {
    val id: SpaceIdentifier
}

/**
 * @d2 command
 * @parent [SpaceDeleteFunction]
 */
data class SpaceDeleteCommand(
    /**
     * Identifier of the space to delete.
     */
    override val id: SpaceIdentifier
): SpaceDeleteCommandDTO

@JsExport
@JsName("SpaceDeletedEventDTO")
interface SpaceDeletedEventDTO: Event {
    val id: SpaceIdentifier
}

/**
 * @d2 event
 * @parent [SpaceDeleteFunction]
 */
data class SpaceDeletedEvent(
    /**
     * Identifier of the deleted space.
     */
    override val id: SpaceIdentifier,
): SpaceDeletedEventDTO
