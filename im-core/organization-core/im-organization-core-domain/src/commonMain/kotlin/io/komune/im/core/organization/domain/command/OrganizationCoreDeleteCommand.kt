package io.komune.im.core.organization.domain.command

import io.komune.im.commons.model.OrganizationId
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
data class OrganizationCoreDeleteCommand(
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
data class OrganizationCoreDeletedEvent(
    override val id: OrganizationId
): OrganizationDeletedEventDTO
