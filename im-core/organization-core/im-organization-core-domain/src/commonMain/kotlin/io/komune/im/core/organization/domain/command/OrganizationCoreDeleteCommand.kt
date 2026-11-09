package io.komune.im.core.organization.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import io.komune.im.commons.model.OrganizationId
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * @d2 command
 */
@JsExport
interface OrganizationCoreDeleteCommandDTO: Command {
    /**
     * Id of the organization to delete.
     */
    val id: OrganizationId
}

/**
 * @d2 inherit
 */
@JsExport
@Serializable
data class OrganizationCoreDeleteCommand(
    override val id: OrganizationId
): OrganizationCoreDeleteCommandDTO

/**
 * @d2 event
 */
@JsExport
interface OrganizationCoreDeletedEventDTO: Event {
    /**
     * Id of the deleted organization.
     */
    val id: OrganizationId
}

/**
 * @d2 inherit
 */
@JsExport
@Serializable
data class OrganizationCoreDeletedEvent(
    override val id: OrganizationId
): OrganizationCoreDeletedEventDTO
