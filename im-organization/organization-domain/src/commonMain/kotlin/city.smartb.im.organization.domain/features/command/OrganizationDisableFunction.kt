package city.smartb.im.organization.domain.features.command

import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Disable an organization along with all its users.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 40
 */
typealias OrganizationDisableFunction = F2Function<OrganizationDisableCommand, OrganizationDisabledEvent>

/**
 * @d2 command
 * @parent [OrganizationDisableFunction]
 */
data class OrganizationDisableCommand(
    /**
     * Identifier of the organization to disable.
     */
    val id: OrganizationId
): Command

/**
 * @d2 event
 * @parent [OrganizationDisableFunction]
 */
data class OrganizationDisabledEvent(
    /**
     * Identifier of the disabled organization.
     */
    val id: OrganizationId,

    /**
     * Identifiers of the disabled users within the organization.
     */
    val userIds: List<String>
): Event
