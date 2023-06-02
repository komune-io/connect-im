package city.smartb.im.organization.domain.features.command

import city.smartb.im.organization.domain.model.ApiKeyId
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Remove an API key from an organization.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 60
 */
typealias OrganizationRemoveApiKeyFunction = F2Function<OrganizationRemoveApiKeyCommand, OrganizationRemovedApiKeyEvent>

@JsExport
@JsName("OrganizationRemoveApiKeyCommandDTO")
interface OrganizationRemoveApiKeyCommandDTO: Command {
    val id: OrganizationId
    val keyId: ApiKeyId
}

/**
 * @d2 command
 * @parent [OrganizationRemoveApiKeyFunction]
 */
data class OrganizationRemoveApiKeyCommand(
    /**
     * Id of the organization.
     */
    override val id: OrganizationId,

    /**
     * Id of the key.
     */
    override val keyId: ApiKeyId,
): OrganizationRemoveApiKeyCommandDTO

@JsExport
@JsName("OrganizationRemovedApiKeyEventDTO")
interface OrganizationRemovedApiKeyEventDTO: Event {
    val id: OrganizationId
    val keyId: ApiKeyId
}

/**
 * @d2 event
 * @parent [OrganizationRemoveApiKeyFunction]
 */
data class OrganizationRemovedApiKeyEvent(
    /**
     * Id of the organization.
     */
    override val id: OrganizationId,

    /**
     * Id of the removed key.
     */
    override val keyId: ApiKeyId
): OrganizationRemovedApiKeyEventDTO
