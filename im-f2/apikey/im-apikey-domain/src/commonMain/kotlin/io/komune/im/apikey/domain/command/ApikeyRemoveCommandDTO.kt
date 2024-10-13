package io.komune.im.apikey.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.commons.model.OrganizationId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Create an API key for an apikey.
 * @d2 function
 * @parent [io.komune.im.apikey.domain.D2ApiKeyPage]
 * @order 60
 */
typealias ApikeyRemoveFunction = F2Function<ApikeyRemoveCommand, ApikeyRemoveEvent>

@JsExport
@JsName("ApikeyRemoveCommandDTO")
interface ApikeyRemoveCommandDTO: Command {
    val id: ApiKeyId
}

/**
 * @d2 command
 * @parent [ApikeyRemoveFunction]
 */
@Serializable
data class ApikeyRemoveCommand(
    /**
     * Id of the apikey.
     */
    override val id: ApiKeyId
): ApikeyRemoveCommandDTO

@JsExport
@JsName("ApikeyRemoveEventDTO")
interface ApikeyRemoveEventDTO: Event {
    val id: ApiKeyId
    val organizationId: OrganizationId
}

/**
 * @d2 event
 * @parent [ApikeyRemoveFunction]
 */
@Serializable
data class ApikeyRemoveEvent(
    /**
     * Id of the apikey.
     */
    override val id: ApiKeyId,
    /**
     * Identifier of the organizationId.
     */
    override val organizationId: OrganizationId
): ApikeyRemoveEventDTO
