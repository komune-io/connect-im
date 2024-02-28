package io.komune.im.f2.organization.domain.command

import io.komune.im.commons.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Update the logo of an organization.
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 * @order 30
 */
typealias OrganizationUploadLogoFunction = F2Function<OrganizationUploadLogoCommand, OrganizationUploadedLogoEvent>

@JsExport
@JsName("OrganizationUploadLogoCommandDTO")
interface OrganizationUploadLogoCommandDTO: Command {
    val id: OrganizationId
}

/**
 * @d2 command
 * @parent [OrganizationUploadLogoFunction]
 */
data class OrganizationUploadLogoCommand(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId
): OrganizationUploadLogoCommandDTO

@JsExport
@JsName("OrganizationUploadedLogoEventDTO")
interface OrganizationUploadedLogoEventDTO: Event {
    val id: OrganizationId
    val url: String
}

/**
 * @d2 event
 * @parent [OrganizationUploadLogoFunction]
 */
data class OrganizationUploadedLogoEvent(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * Public URL of the newly uploaded logo
     */
    override val url: String
): OrganizationUploadedLogoEventDTO
