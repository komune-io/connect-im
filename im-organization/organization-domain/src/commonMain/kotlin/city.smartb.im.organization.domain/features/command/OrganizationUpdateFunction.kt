package city.smartb.im.organization.domain.features.command

import city.smartb.im.commons.model.AddressBase
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Updates an organization.
 * @d2 section
 * @parent [city.smartb.im.organization.domain.D2OrganizationCommandSection]
 */
typealias OrganizationUpdateFunction = F2Function<OrganizationUpdateCommand, OrganizationUpdatedResult>

/**
 * @d2 command
 * @parent [OrganizationUpdateFunction]
 */
data class OrganizationUpdateCommand(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId,

    /**
     * Official name of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.name]
     */
    val name: String,

    /**
     * Description of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.description]
     */
    val description: String?,

    /**
     * Address of the organization.
     */
    val address: AddressBase?,

    /**
     * Website of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.website]
     */
    val website: String?,

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [city.smartb.im.organization.domain.model.Organization.roles]
     */
    val roles: List<String>?
): Command

/**
 * @d2 event
 * @parent [OrganizationUpdateFunction]
 */
data class OrganizationUpdatedResult(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId
): Event
