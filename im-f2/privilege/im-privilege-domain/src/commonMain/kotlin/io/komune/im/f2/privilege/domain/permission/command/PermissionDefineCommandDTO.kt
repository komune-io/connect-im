package io.komune.im.f2.privilege.domain.permission.command

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.commons.model.PermissionIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Create or update a permission.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2PermissionPage]
 * @order 10
 */
typealias PermissionDefineFunction = F2Function<PermissionDefineCommand, PermissionDefinedEvent>

/**
 * @d2 command
 * @parent [PermissionDefineFunction]
 */
@JsExport
interface PermissionDefineCommandDTO {
    /**
     * @ref [io.komune.im.f2.privilege.domain.permission.model.Permission.identifier]
     */
    val identifier: PermissionIdentifier

    /**
     * @ref [io.komune.im.f2.privilege.domain.permission.model.Permission.description]
     */
    val description: String

    /**
     * @ref [io.komune.im.f2.privilege.domain.permission.model.Permission.features]
     */
    val features: List<List<FeatureIdentifier>>?
}

/**
 * @d2 inherit
 */
@Serializable
data class PermissionDefineCommand(
    override val identifier: PermissionIdentifier,
    override val description: String,
    override val features: List<List<FeatureIdentifier>>?
): PermissionDefineCommandDTO

/**
 * @d2 event
 * @parent [PermissionDefineFunction]
 */
@JsExport
interface PermissionDefinedEventDTO: Event {
    /**
     * Identifier of the created permission.
     */
    val identifier: PermissionIdentifier
}

/**
 * @d2 inherit
 */
@Serializable
data class PermissionDefinedEvent(
    override val identifier: PermissionIdentifier
): PermissionDefinedEventDTO
