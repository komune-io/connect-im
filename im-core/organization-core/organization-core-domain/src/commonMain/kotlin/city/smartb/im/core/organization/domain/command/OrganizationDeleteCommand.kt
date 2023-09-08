package city.smartb.im.core.organization.domain.command

import city.smartb.im.commons.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import kotlin.js.JsExport

/**
 * @d2 command
 */
@JsExport
interface OrganizationDeleteCommandDTO: Command {
    /**
     * Id of the organization to delete.
     */
    val id: OrganizationId
}

/**
 * @d2 inherit
 */
data class OrganizationDeleteCommand(
    override val id: OrganizationId
): OrganizationDeleteCommandDTO

/**
 * @d2 event
 */
@JsExport
interface OrganizationDeletedEventDTO: Event {
    /**
     * Id of the deleted organization.
     */
    val id: OrganizationId
}

/**
 * @d2 inherit
 */
data class OrganizationDeletedEvent(
    override val id: OrganizationId
): OrganizationDeletedEventDTO
