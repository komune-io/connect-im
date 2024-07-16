package io.komune.im.f2.privilege.domain.feature.command

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.FeatureIdentifier
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Create or update a feature.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2FeaturePage]
 * @order 10
 */
typealias FeatureDefineFunction = F2Function<FeatureDefineCommand, FeatureDefinedEvent>

/**
 * @d2 command
 * @parent [FeatureDefineFunction]
 */
@JsExport
interface FeatureDefineCommandDTO {
    /**
     * @ref [io.komune.im.f2.privilege.domain.feature.model.Feature.identifier]
     */
    val identifier: FeatureIdentifier

    /**
     * @ref [io.komune.im.f2.privilege.domain.feature.model.Feature.description]
     */
    val description: String
}

/**
 * @d2 inherit
 */
@Serializable
data class FeatureDefineCommand(
    override val identifier: FeatureIdentifier,
    override val description: String,
): FeatureDefineCommandDTO

/**
 * @d2 event
 * @parent [FeatureDefineFunction]
 */
@JsExport
interface FeatureDefinedEventDTO: Event {
    /**
     * Identifier of the created feature.
     */
    val identifier: FeatureIdentifier
}

/**
 * @d2 inherit
 */
@Serializable
data class FeatureDefinedEvent(
    override val identifier: FeatureIdentifier
): FeatureDefinedEventDTO
