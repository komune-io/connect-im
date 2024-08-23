package io.komune.im.core.organization.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import io.komune.im.commons.model.OrganizationId
import kotlinx.serialization.Serializable
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
@Serializable
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
@Serializable
data class OrganizationCoreDeletedEvent(
    override val id: OrganizationId
): OrganizationDeletedEventDTO
