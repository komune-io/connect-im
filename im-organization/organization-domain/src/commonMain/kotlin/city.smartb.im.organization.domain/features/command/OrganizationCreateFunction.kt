package city.smartb.im.organization.domain.features.command

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create a new organization.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 10
 */
typealias OrganizationCreateFunction = F2Function<OrganizationCreateCommand, OrganizationCreatedEvent>

@JsExport
@JsName("OrganizationCreateCommandDTO")
interface OrganizationCreateCommandDTO: Command {
    val siret: String?
    val name: String
    val description: String?
    val address: AddressDTO?
    val website: String?
    val roles: List<String>?
    val parentOrganizationId: OrganizationId?
    val attributes: Map<String, String>?
}

/**
 * @d2 command
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreateCommand(
    /**
     * Siret number of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.siret]
     */
    override val siret: String?,

    /**
     * Official name of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.name]
     */
    override val name: String,

    /**
     * Description of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.description]
     */
    override val description: String?,

    /**
     * Address of the organization.
     */
    override val address: Address?,

    /**
     * Website of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.website]
     */
    override val website: String?,

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [city.smartb.im.organization.domain.model.Organization.roles]
     */
    override val roles: List<String>?,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    override val parentOrganizationId: OrganizationId? = null,

    /**
     * Additional arbitrary attributes assigned to the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.attributes]
     */
    override val attributes: Map<String, String>?,
): OrganizationCreateCommandDTO

@JsExport
@JsName("OrganizationCreatedEventDTO")
interface OrganizationCreatedEventDTO: Event {
    val id: OrganizationId
    val parentOrganization: OrganizationId?
}

/**
 * @d2 event
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreatedEvent(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    override val parentOrganization: OrganizationId? = null
): OrganizationCreatedEventDTO
