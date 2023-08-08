package city.smartb.im.role.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName

/**
 * Create a new role.
 * @d2 function
 * @parent [city.smartb.im.role.domain.D2RolePage]
 * @order 10
 */
typealias RoleCreateFunction = F2Function<RoleCreateCommand, RoleCreatedEvent>

typealias KeycloakRoleCreateCommand = i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
typealias KeycloakRoleCreateFunction = i2.keycloak.f2.role.domain.features.command.RoleCreateFunction

/**
 * @d2 command
 * @parent [RoleCreateFunction]
 */
data class RoleCreateCommand(
    /**
     * Name of the role.
     */
    val name: RoleName,

    /**
     * Roles to associate with the role. These roles must exist to be associated.
     * @example [["write_user","read_user"]]
     */
    val composites: List<RoleName> = emptyList(),

    /**
     * Description of the role.
     * @example [i2.keycloak.f2.role.domain.RoleModel.description]
     */
    val description: String? = null,

    /**
     * Whether it is a client role or not.
     * @example [i2.keycloak.f2.role.domain.RoleModel.isClientRole]
     */
    val isClientRole: Boolean = false,
): Command

/**
 * @d2 event
 * @parent [RoleCreateFunction]
 */
data class RoleCreatedEvent(
    /**
     * Identifier of the role created.
     */
    val id: RoleId
): Event
