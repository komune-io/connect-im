package city.smartb.im.apikey.domain.command

import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.commons.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create an API key for an apikey.
 * @d2 function
 * @parent [city.smartb.im.apikey.domain.D2ApiKeyPage]
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
