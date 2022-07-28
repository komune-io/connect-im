package city.smartb.im.organization.domain.features.command

import city.smartb.im.commons.model.Address
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Create a new organization.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 10
 */
typealias OrganizationCreateFunction = F2Function<OrganizationCreateCommand, OrganizationCreatedEvent>

/**
 * @d2 command
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreateCommand(
    /**
     * Siret number of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.siret]
     */
    val siret: String?,

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
    val address: Address?,

    /**
     * Website of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.website]
     */
    val website: String?,

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [city.smartb.im.organization.domain.model.Organization.roles]
     */
    val roles: List<String>?,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    val parentOrganizationId: OrganizationId? = null,

    /**
     * Additional arbitrary attributes assigned to the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.attributes]
     */
    val attributes: Map<String, String>?
): Command

/**
 * @d2 event
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreatedEvent(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    val parentOrganization: OrganizationId? = null
): Event
