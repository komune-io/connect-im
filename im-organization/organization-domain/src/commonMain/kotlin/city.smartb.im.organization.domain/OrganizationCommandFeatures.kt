package city.smartb.im.organization.domain

import city.smartb.im.organization.domain.features.command.OrganizationCreateFunction
import city.smartb.im.organization.domain.features.command.OrganizationDisableFunction
import city.smartb.im.organization.domain.features.command.OrganizationUpdateFunction

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
}
