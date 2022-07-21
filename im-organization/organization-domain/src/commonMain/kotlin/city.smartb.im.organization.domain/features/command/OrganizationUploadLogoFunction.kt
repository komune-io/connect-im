package city.smartb.im.organization.domain.features.command

import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Update the logo of an organization.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 30
 */
typealias OrganizationUploadLogoFunction = F2Function<OrganizationUploadLogoCommand, OrganizationUploadedLogoEvent>

/**
 * @d2 command
 * @parent [OrganizationUploadLogoFunction]
 */
data class OrganizationUploadLogoCommand(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId
): Command

/**
 * @d2 event
 * @parent [OrganizationUploadLogoFunction]
 */
data class OrganizationUploadedLogoEvent(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId,

    /**
     * Public URL of the newly uploaded logo
     */
    val url: String
): Event
