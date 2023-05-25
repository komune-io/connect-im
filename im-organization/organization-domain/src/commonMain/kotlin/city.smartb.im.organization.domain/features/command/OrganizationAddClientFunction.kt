package city.smartb.im.organization.domain.features.command

import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create an API client for an organization.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 60
 */
typealias OrganizationAddClientFunction = F2Function<OrganizationAddClientCommand, OrganizationAddedClientEvent>

@JsExport
@JsName("OrganizationAddClientCommandDTO")
interface OrganizationAddClientCommandDTO: Command {
    val id: OrganizationId
    val name: String
}

/**
 * @d2 command
 * @parent [OrganizationAddClientFunction]
 */
data class OrganizationAddClientCommand(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * Name of the client.
     * @example [city.smartb.im.organization.domain.model.Organization.name]
     */
    override val name: String,
): OrganizationAddClientCommandDTO

@JsExport
@JsName("OrganizationAddedClientEventDTO")
interface OrganizationAddedClientEventDTO: Event {
    val id: OrganizationId
    val clientIdentifier: String
}

/**
 * @d2 event
 * @parent [OrganizationAddClientFunction]
 */
data class OrganizationAddedClientEvent(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * Identifier of the created client.
     */
    override val clientIdentifier: String
): OrganizationAddedClientEventDTO
