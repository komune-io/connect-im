package city.smartb.im.f2.privilege.domain.role.command

import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.commons.model.RoleIdentifier
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Create or update a role.
 * @d2 function
 * @parent [city.smartb.im.f2.privilege.domain.D2RolePage]
 * @order 10
 */
typealias RoleDefineFunction = F2Function<RoleDefineCommand, RoleDefinedEvent>

/**
 * @d2 command
 * @parent [RoleDefineFunction]
 */
@JsExport
interface RoleDefineCommandDTO {
    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.Role.identifier]
     */
    val identifier: RoleIdentifier

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.Role.description]
     */
    val description: String

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.Role.targets]
     */
    val targets: List<String>

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.Role.locale]
     */
    val locale: Map<String, String>

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.Role.bindings]
     */
    val bindings: Map<String, List<RoleIdentifier>>?

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.Role.permissions]
     */
    val permissions: List<PermissionIdentifier>?
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleDefineCommand(
    override val identifier: RoleIdentifier,
    override val description: String,
    override val targets: List<String>,
    override val locale: Map<String, String>,
    override val bindings: Map<String, List<RoleIdentifier>>?,
    override val permissions: List<PermissionIdentifier>?
): RoleDefineCommandDTO

/**
 * @d2 event
 * @parent [RoleDefineFunction]
 */
@JsExport
interface RoleDefinedEventDTO: Event {
    /**
     * Identifier of the created role.
     */
    val identifier: RoleIdentifier
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleDefinedEvent(
    override val identifier: RoleIdentifier
): RoleDefinedEventDTO
