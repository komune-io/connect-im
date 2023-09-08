package city.smartb.im.f2.organization.domain.command

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.commons.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create a new organization.
 * @d2 function
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @order 10
 */
typealias OrganizationCreateFunction = F2Function<OrganizationCreateCommandDTOBase, OrganizationCreatedEvent>

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
    val withApiKey: Boolean
}

/**
 * @d2 command
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreateCommandDTOBase(
    /**
     * Siret number of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.siret]
     */
    override val siret: String? = null,

    /**
     * Official name of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.name]
     */
    override val name: String,

    /**
     * Description of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.description]
     */
    override val description: String? = null,

    /**
     * Address of the organization.
     */
    override val address: Address? = null,

    /**
     * Website of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.website]
     */
    override val website: String? = null,

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.roles]
     */
    override val roles: List<String>? = null,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    override val parentOrganizationId: OrganizationId? = null,

    /**
     * Additional arbitrary attributes assigned to the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.attributes]
     */
    override val attributes: Map<String, String>? = null,

    /**
     * Whether to create a default API key for the new organization.
     * @example false
     */
    override val withApiKey: Boolean = false
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
