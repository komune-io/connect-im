package io.komune.im.f2.space.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.SpaceIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Create or update a space.
 * @d2 function
 * @parent [io.komune.im.f2.space.domain.D2SpacePage]
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
     * SMTP is a variable that represents the configuration of
     * the Simple Mail Transfer Protocol (SMTP) settings as a map.
     *
     */
    val smtp: Map<String, String>?

    /**
     * List of supported locales.
     * @example [["en", "fr"]]
     */
    val locales: List<String>?
    val settings: SpaceSettingsDTO?
}

/**
 * @d2 inherit
 */
@Serializable
data class SpaceDefineCommand(
    override val identifier: String,
    override val theme: String?,
    override val smtp: Map<String, String>?,
    override val locales: List<String>?,
    override val settings: SpaceSettings?
): SpaceDefineCommandDTO

/**
 * @d2 inherit
 */
@JsExport
interface SpaceSettingsDTO {
    val registrationAllowed: Boolean?
    val rememberMe: Boolean?
    val resetPasswordAllowed: Boolean?
}

/**
 * @d2 inherit
 */
@Serializable
data class SpaceSettings(
    override val registrationAllowed: Boolean?,
    override val rememberMe: Boolean?,
    override val resetPasswordAllowed: Boolean?
): SpaceSettingsDTO

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
@Serializable
data class SpaceDefinedEvent(
    override val identifier: SpaceIdentifier,
): SpaceDefinedEventDTO
