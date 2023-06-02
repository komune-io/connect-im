package city.smartb.im.organization.domain.features.command

import city.smartb.im.organization.domain.model.ApiKeyId
import city.smartb.im.organization.domain.model.ApiKeyIdentifier
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create an API key for an organization.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 60
 */
typealias OrganizationAddApiKeyFunction = F2Function<OrganizationAddApiKeyCommand, OrganizationAddedApiKeyEvent>

@JsExport
@JsName("OrganizationAddApiKeyCommandDTO")
interface OrganizationAddApiKeyCommandDTO: Command {
    val id: OrganizationId
    val name: String
}

/**
 * @d2 command
 * @parent [OrganizationAddApiKeyFunction]
 */
data class OrganizationAddApiKeyCommand(
    /**
     * Id of the organization.
     */
    override val id: OrganizationId,

    /**
     * Name of the key.
     */
    override val name: String,
): OrganizationAddApiKeyCommandDTO

@JsExport
@JsName("OrganizationAddedApiKeyEventDTO")
interface OrganizationAddedApiKeyEventDTO: Event {
    val id: OrganizationId
    val keyId: ApiKeyId
    val keyIdentifier: ApiKeyIdentifier
    val keySecret: String
}

/**
 * @d2 event
 * @parent [OrganizationAddApiKeyFunction]
 */
data class OrganizationAddedApiKeyEvent(
    /**
     * Id of the organization.
     */
    override val id: OrganizationId,

    /**
     * Id of the new key.
     */
    override val keyId: ApiKeyId,

    /**
     * Identifier of the new key.
     */
    override val keyIdentifier: ApiKeyIdentifier,

    /**
     * Secret of the new key.
     */
    override val keySecret: String
): OrganizationAddedApiKeyEventDTO
