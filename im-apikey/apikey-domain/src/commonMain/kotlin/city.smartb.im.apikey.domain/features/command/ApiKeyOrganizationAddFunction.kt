package city.smartb.im.apikey.domain.features.command

import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.apikey.domain.model.ApiKeyIdentifier
import city.smartb.im.commons.auth.OrganizationId
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
typealias ApiKeyOrganizationAddFunction = F2Function<ApiKeyOrganizationAddKeyCommand, ApiKeyOrganizationAddedEvent>

@JsExport
@JsName("ApiKeyOrganizationAddCommandDTO")
interface ApiKeyOrganizationAddCommandDTO: Command {
    val organizationId: OrganizationId
    val name: String
}

/**
 * @d2 command
 * @parent [ApiKeyOrganizationAddFunction]
 */
data class ApiKeyOrganizationAddKeyCommand(
    /**
     * Id of the organization.
     */
    override val organizationId: OrganizationId,

    /**
     * Name of the key.
     */
    override val name: String,
): ApiKeyOrganizationAddCommandDTO

@JsExport
@JsName("ApiKeyAddedEventDTO")
interface ApiKeyAddedEventDTO: Event {
    val organizationId: OrganizationId
    val keyId: ApiKeyId
    val keyIdentifier: ApiKeyIdentifier
    val keySecret: String
}

/**
 * @d2 event
 * @parent [ApiKeyOrganizationAddFunction]
 */
data class ApiKeyOrganizationAddedEvent(
    /**
     * Id of the apikey.
     */
    override val organizationId: OrganizationId,

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
): ApiKeyAddedEventDTO
