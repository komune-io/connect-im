package city.smartb.im.f2.space.domain.command

import city.smartb.im.commons.model.SpaceIdentifier
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport

/**
 * Create or update a space.
 * @d2 function
 * @parent [city.smartb.im.f2.space.domain.D2SpacePage]
 * @order 10
 */
typealias SpaceDefineFunction = F2Function<SpaceDefineCommand, SpaceDefinedEvent>

/**
 * @d2 command
 * @parent [SpaceDefineFunction]
 */
@JsExport
interface SpaceDefineCommandDTO: Command {
    /**
     * Identifier of the space to create.
     */
    val identifier: SpaceIdentifier

    /**
     * Name of the theme to use for the space.
     * @example "im"
     */
    val theme: String?

    /**
     * List of supported locales.
     * @example [["en", "fr"]]
     */
    val locales: List<String>?
}

/**
 * @d2 inherit
 */
data class SpaceDefineCommand(
    override val identifier: String,
    override val theme: String?,
    override val locales: List<String>?
): SpaceDefineCommandDTO

/**
 * @d2 event
 * @parent [SpaceDefineFunction]
 */
@JsExport
interface SpaceDefinedEventDTO: Event {
    /**
     * Identifier of the defined space.
     */
    val identifier: SpaceIdentifier
}

/**
 * @d2 inherit
 */
data class SpaceDefinedEvent(
    override val identifier: SpaceIdentifier,
): SpaceDefinedEventDTO
