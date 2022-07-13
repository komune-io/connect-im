package city.smartb.im.organization.domain.features.command

import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Updates the logo of the organization.
 * @d2 section
 * @parent [city.smartb.im.organization.domain.D2OrganizationCommandSection]
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
