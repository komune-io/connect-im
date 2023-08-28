package city.smartb.im.f2.privilege.domain.role.command

import city.smartb.im.commons.model.ImCommand
import city.smartb.im.f2.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
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
typealias RoleDefineFunction = F2Function<RoleDefineCommandDTOBase, RoleDefinedEventDTOBase>

/**
 * @d2 command
 * @parent [RoleDefineFunction]
 */
@JsExport
interface RoleDefineCommandDTO: ImCommand {
    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase.identifier]
     */
    val identifier: RoleIdentifier

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase.description]
     */
    val description: String

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase.targets]
     */
    val targets: List<String>

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase.locale]
     */
    val locale: Map<String, String>

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase.bindings]
     */
    val bindings: Map<String, List<RoleIdentifier>>?

    /**
     * @ref [city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase.permissions]
     */
    val permissions: List<PermissionIdentifier>?
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleDefineCommandDTOBase(
    override val realmId: String? = null,
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
data class RoleDefinedEventDTOBase(
    override val identifier: RoleIdentifier
): RoleDefinedEventDTO
