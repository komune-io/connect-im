package city.smartb.im.role.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleName

/**
 * Adds composites roles to a role.
 * @d2 section
 * @parent [city.smartb.im.role.domain.D2RoleCommandSection]
 */
typealias RoleAddCompositesFunction = F2Function<RoleAddCompositesCommand, RoleAddedCompositesEvent>

typealias KeycloakRoleAddCompositesCommand = i2.keycloak.f2.role.domain.features.command.RoleAddCompositesCommand
typealias KeycloakRoleAddCompositesFunction = i2.keycloak.f2.role.domain.features.command.RoleAddCompositesFunction

/**
 * @d2 command
 * @parent [RoleAddCompositesFunction]
 */
data class RoleAddCompositesCommand(
    /**
     * Name of the role to add the composites roles to.
     * @example "Carrier"
     */
    val roleName: RoleName,

    /**
     * List of roles that are associated with the role.
     * @example [["write_user", "read_user"]]
     */
    val composites: List<RoleName>,
): Command

/**
 * @d2 event
 * @parent [RoleAddCompositesFunction]
 */
data class RoleAddedCompositesEvent(
    /**
     * Name of the role that composites were added to.
     * @example [RoleAddCompositesCommand.roleName]
     */
    val roleName: RoleName
): Event
