package city.smartb.im.organization.domain.features.command

import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Delete an organization (but not its users).
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 50
 */
typealias OrganizationDeleteFunction = F2Function<OrganizationDeleteCommand, OrganizationDeletedEvent>

@JsExport
@JsName("OrganizationDeleteCommandDTO")
interface OrganizationDeleteCommandDTO: Command {
    val id: OrganizationId
}

/**
 * @d2 command
 * @parent [OrganizationDeleteFunction]
 */
data class OrganizationDeleteCommand(
    /**
     * Identifier of the organization to delete.
     */
    override val id: OrganizationId
): OrganizationDeleteCommandDTO

@JsExport
@JsName("OrganizationDeletedEventDTO")
interface OrganizationDeletedEventDTO: Event {
    val id: OrganizationId
}

/**
 * @d2 event
 * @parent [OrganizationDeleteFunction]
 */
data class OrganizationDeletedEvent(
    /**
     * Identifier of the deleted organization.
     */
    override val id: OrganizationId,
): OrganizationDeletedEventDTO
