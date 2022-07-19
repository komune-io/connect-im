package city.smartb.im.role.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName

/**
 * Updates a role.
 * @d2 function
 * @parent [city.smartb.im.role.domain.D2RoleCommandSection]
 */
typealias RoleUpdateFunction = F2Function<RoleUpdateCommand, RoleUpdatedEvent>

typealias KeycloakRoleUpdateCommand = i2.keycloak.f2.role.domain.features.command.RoleUpdateCommand
typealias KeycloakRoleUpdateFunction = i2.keycloak.f2.role.domain.features.command.RoleUpdateFunction

/**
 * @d2 command
 * @parent [RoleUpdateFunction]
 */
data class RoleUpdateCommand(
    /**
     * Name of the role.
     */
    val name: RoleName,

    /**
     * Roles to associate with the role. These roles must exist to be associated.
     * It removes all the composite roles existing, and replaces it by the given composite roles.
     * @example [["write_user","read_user"]]
     */
    val composites: List<RoleName>,

    /**
     * Description of the role.
     * @example [i2.keycloak.f2.role.domain.RoleModel.description]
     */
    val description: String?,

    /**
     * Whether it is a client role or not.
     * @example [i2.keycloak.f2.role.domain.RoleModel.isClientRole]
     */
    val isClientRole: Boolean
): Command

/**
 * @d2 event
 * @parent [RoleUpdateFunction]
 */
data class RoleUpdatedEvent(
    /**
     * Identifier of the role.
     */
    val id: RoleId
): Event
