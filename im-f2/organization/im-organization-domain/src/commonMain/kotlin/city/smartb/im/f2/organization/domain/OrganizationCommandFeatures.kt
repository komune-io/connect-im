package city.smartb.im.f2.organization.domain

import city.smartb.im.f2.organization.domain.command.OrganizationCreateFunction
import city.smartb.im.f2.organization.domain.command.OrganizationDeleteFunction
import city.smartb.im.f2.organization.domain.command.OrganizationDisableFunction
import city.smartb.im.f2.organization.domain.command.OrganizationUpdateFunction

interface OrganizationCommandFeatures {

    /**
     * Create an organization.
     */
    fun organizationCreate(): OrganizationCreateFunction

    /**
     * Update an organization.
     */
    fun organizationUpdate(): OrganizationUpdateFunction

    /**
     * Upload a logo for a given organization
     */
//    suspend fun organizationUploadLogo(cmd: OrganizationUploadLogoCommand, file: FilePart): OrganizationUploadedLogoEvent

    /**
     * Disable an organization and its users.
     */
    fun organizationDisable(): OrganizationDisableFunction

    /**
     * Delete an organization.
     */
    fun organizationDelete(): OrganizationDeleteFunction
}
