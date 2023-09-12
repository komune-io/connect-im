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
 * Update an organization.
 * @d2 function
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @order 20
 */
typealias OrganizationUpdateFunction = F2Function<OrganizationUpdateCommand, OrganizationUpdatedResult>

/**
 * @d2 command
 * @parent [OrganizationUpdateFunction]
 */
@JsExport
@JsName("OrganizationUpdateCommandDTO")
interface OrganizationUpdateCommandDTO: Command {
    /**
     * Id of the organization.
     */
    val id: OrganizationId

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
     * Additional arbitrary attributes assigned to the organization.
     * @example [city.smartb.im.f2.organization.domain.model.Organization.attributes]
     */
    val attributes: Map<String, String>?

    /**
     * @ref [city.smartb.im.f2.organization.domain.model.Organization.status]
     */
    val status: String
}

data class OrganizationUpdateCommand(
    override val id: OrganizationId,
    override val name: String,
    override val description: String?,
    override val address: Address?,
    override val website: String?,
    override val roles: List<String>?,
    override val attributes: Map<String, String>?,
    override val status: String
): OrganizationUpdateCommandDTO

/**
 * @d2 event
 * @parent [OrganizationUpdateFunction]
 */
@JsExport
@JsName("OrganizationUpdatedResultDTO")
interface OrganizationUpdatedResultDTO: Event {
    /**
     * Id of the updated organization.
     */
    val id: OrganizationId
}

data class OrganizationUpdatedResult(
    override val id: OrganizationId
): OrganizationUpdatedResultDTO
