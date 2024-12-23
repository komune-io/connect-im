package io.komune.im.f2.organization.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.OrganizationId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Disable an organization along with all its users.
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 * @order 40
 */
typealias OrganizationDisableFunction = F2Function<OrganizationDisableCommand, OrganizationDisabledEvent>

@JsExport
@JsName("OrganizationDisableCommandDTO")
interface OrganizationDisableCommandDTO: Command {
    /**
     * Identifier of the organization to disable.
     */
    val id: OrganizationId

    /**
     * Identifier of the user executing the command.
     * @example "d61e6ab6-12d2-40ec-ba15-534aa7302a2b"
     */
    val disabledBy: String?

    /**
     * Whether to anonymize the personal data of the organization or not.
     * This will remove or blank all sensible fields except custom attributes.
     * To anonymize custom attributes, see `attributes` field.
     * @example true
     */
    val anonymize: Boolean

    /**
     * Custom attributes to update during anonymization.
     * Use this field only if `anonymize` is set to true.
     * @example { "age": null }
     */
    val attributes: Map<String, String>?

    /**
     * Custom attributes of the users of the organization to update during anonymization.
     * Use this field only if `anonymize` is set to true.
     * @example { "age": null }
     */
    val userAttributes: Map<String, String>?
}

/**
 * @d2 command
 * @parent [OrganizationDisableFunction]
 */
@Serializable
data class OrganizationDisableCommand(
    override val id: OrganizationId,
    override val disabledBy: String?,
    override val anonymize: Boolean,
    override val attributes: Map<String, String>?,
    override val userAttributes: Map<String, String>?
): OrganizationDisableCommandDTO

@JsExport
@JsName("OrganizationDisabledEventDTO")
interface OrganizationDisabledEventDTO: Event {
    val id: OrganizationId
    val userIds: List<String>
}

/**
 * @d2 event
 * @parent [OrganizationDisableFunction]
 */
@Serializable
data class OrganizationDisabledEvent(
    /**
     * Identifier of the disabled organization.
     */
    override val id: OrganizationId,

    /**
     * Identifiers of the disabled users within the organization.
     */
    override val userIds: List<String>
): OrganizationDisabledEventDTO
