package city.smartb.im.apikey.domain.command

import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.apikey.domain.model.ApiKeyIdentifier
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport

/**
 * Create an API key for an apikey.
 * @d2 function
 * @parent [city.smartb.im.apikey.domain.D2ApiKeyPage]
 * @order 60
 */
typealias ApiKeyOrganizationAddFunction = F2Function<ApiKeyOrganizationAddKeyCommand, ApiKeyOrganizationAddedEvent>

/**
 * @d2 command
 * @parent [ApiKeyOrganizationAddFunction]
 */
@JsExport
interface ApiKeyOrganizationAddCommandDTO: Command {
    /**
     * Id of the organization.
     */
    val organizationId: OrganizationId

    /**
     * Name of the key.
     */
    val name: String

    /**
     * Roles to assign to the key.
     * @example [["tr_orchestrator_user"]]
     */
    val roles: List<RoleIdentifier>
}

/**
 * @d2 inherit
 */
data class ApiKeyOrganizationAddKeyCommand(
    override val organizationId: OrganizationId,
    override val name: String,
    override val roles: List<RoleIdentifier>
): ApiKeyOrganizationAddCommandDTO

/**
 * @d2 event
 * @parent [ApiKeyOrganizationAddFunction]
 */
@JsExport
interface ApiKeyAddedEventDTO: Event {
    /**
     * Id of the new key.
     */
    val organizationId: OrganizationId

    /**
     * Id of the organization.
     */
    val id: ApiKeyId

    /**
     * Identifier of the new key.
     */
    val keyIdentifier: ApiKeyIdentifier

    /**
     * Secret of the new key.
     */
    val keySecret: String
}

/**
 * @d2 inherit
 */
data class ApiKeyOrganizationAddedEvent(
    override val id: ApiKeyId,
    override val organizationId: OrganizationId,
    override val keyIdentifier: ApiKeyIdentifier,
    override val keySecret: String
): ApiKeyAddedEventDTO
