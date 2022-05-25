package city.smartb.im.organization.domain.features.command

import city.smartb.im.commons.ImMessage
import city.smartb.im.commons.model.AddressBase
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Creates a new organization.
 * @d2 section
 * @parent [city.smartb.im.organization.domain.D2OrganizationCommandSection]
 */
typealias OrganizationCreateFunction = F2Function<ImMessage<OrganizationCreateCommand>, OrganizationCreateResult>

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
    val roles: List<String>?,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    val parentOrganizationId: OrganizationId? = null
): Command

/**
 * @d2 event
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreateResult(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    val parentOrganization: OrganizationId? = null
): Event
