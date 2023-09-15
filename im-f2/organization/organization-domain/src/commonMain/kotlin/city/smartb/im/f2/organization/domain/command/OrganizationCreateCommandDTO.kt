package city.smartb.im.f2.organization.domain.command

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.organization.domain.model.OrganizationStatusValues
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
typealias OrganizationCreateFunction = F2Function<OrganizationCreateCommand, OrganizationCreatedEvent>

/**
 * @d2 command
 * @parent [OrganizationCreateFunction]
 */
@JsExport
interface OrganizationCreateCommandDTO: Command {
    /**
     * Siret number of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.Organization.siret]
     */
    val siret: String?

    /**
     * Official name of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.Organization.name]
     */
    val name: String

    /**
     * Description of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.Organization.description]
     */
    val description: String?

    /**
     * Address of the organization.
     */
    val address: AddressDTO?

    /**
     * Website of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.Organization.website]
     */
    val website: String?

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [city.smartb.im.f2.organization.domain.model.Organization.roles]
     */
    val roles: List<String>?

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    val parentOrganizationId: OrganizationId?

    /**
     * Additional arbitrary attributes assigned to the organization.
     * @example [city.smartb.im.f2.organization.domain.model.Organization.attributes]
     */
    val attributes: Map<String, String>?

    /**
     * @ref [city.smartb.im.f2.organization.domain.model.Organization.status]
     * @default "PENDING"
     */
    val status: String?
}

data class OrganizationCreateCommand(
    override val siret: String? = null,
    override val name: String,
    override val description: String? = null,
    override val address: Address? = null,
    override val website: String? = null,
    override val roles: List<String>? = null,
    override val parentOrganizationId: OrganizationId? = null,
    override val attributes: Map<String, String>? = null,
    override val status: String = OrganizationStatusValues.pending()
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
