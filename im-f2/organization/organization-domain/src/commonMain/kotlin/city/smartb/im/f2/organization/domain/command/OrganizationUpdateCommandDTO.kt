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

@JsExport
@JsName("OrganizationUpdateCommandDTO")
interface OrganizationUpdateCommandDTO: Command {
    val id: OrganizationId
    val name: String
    val description: String?
    val address: AddressDTO?
    val website: String?
    val roles: List<String>?
    val attributes: Map<String, String>?
}

/**
 * @d2 command
 * @parent [OrganizationUpdateFunction]
 */
data class OrganizationUpdateCommand(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * Official name of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.name]
     */
    override val name: String,

    /**
     * Description of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.description]
     */
    override val description: String?,

    /**
     * Address of the organization.
     */
    override val address: Address?,

    /**
     * Website of the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.website]
     */
    override val website: String?,

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.roles]
     */
    override val roles: List<String>?,

    /**
     * Additional arbitrary attributes assigned to the organization.
     * @example [city.smartb.im.f2.organization.domain.model.OrganizationDTOBase.attributes]
     */
    override val attributes: Map<String, String>?
): OrganizationUpdateCommandDTO

@JsExport
@JsName("OrganizationUpdatedResultDTO")
interface OrganizationUpdatedResultDTO: Event {
    val id: OrganizationId
}

/**
 * @d2 event
 * @parent [OrganizationUpdateFunction]
 */
data class OrganizationUpdatedResult(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId
): OrganizationUpdatedResultDTO
