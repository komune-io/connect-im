package io.komune.im.f2.privilege.domain.role.command

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.commons.model.RoleIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Create or update a role.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2RolePage]
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
     * @ref [io.komune.im.f2.privilege.domain.role.model.Role.identifier]
     */
    val identifier: RoleIdentifier

    /**
     * @ref [io.komune.im.f2.privilege.domain.role.model.Role.description]
     */
    val description: String

    /**
     * @ref [io.komune.im.f2.privilege.domain.role.model.Role.targets]
     */
    val targets: List<String>

    /**
     * @ref [io.komune.im.f2.privilege.domain.role.model.Role.locale]
     */
    val locale: Map<String, String>

    /**
     * @ref [io.komune.im.f2.privilege.domain.role.model.Role.bindings]
     */
    val bindings: Map<String, List<RoleIdentifier>>?

    /**
     * @ref [io.komune.im.f2.privilege.domain.role.model.Role.permissions]
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
