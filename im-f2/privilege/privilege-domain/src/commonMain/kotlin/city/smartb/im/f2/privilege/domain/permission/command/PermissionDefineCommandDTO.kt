package city.smartb.im.f2.privilege.domain.permission.command

import city.smartb.im.f2.privilege.domain.permission.model.PermissionIdentifier
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Create or update a permission.
 * @d2 function
 * @parent [city.smartb.im.f2.privilege.domain.D2PermissionPage]
 * @order 10
 */
typealias PermissionDefineFunction = F2Function<PermissionDefineCommandDTOBase, PermissionDefinedEventDTOBase>

/**
 * @d2 command
 * @parent [PermissionDefineFunction]
 */
@JsExport
interface PermissionDefineCommandDTO {
    /**
     * @ref [city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase.identifier]
     */
    val identifier: PermissionIdentifier

    /**
     * @ref [city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase.description]
     */
    val description: String
}

/**
 * @d2 inherit
 */
@Serializable
data class PermissionDefineCommandDTOBase(
    override val identifier: PermissionIdentifier,
    override val description: String,
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
data class PermissionDefinedEventDTOBase(
    override val identifier: PermissionIdentifier
): PermissionDefinedEventDTO
