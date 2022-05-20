package city.smartb.im.organization.domain.model

/**
 * Representation of the organization.
 * @D2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationRefModelSection]
 */
interface OrganizationRefDTO {
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId
    /**
     * Name of the organization.
     * @example "SmartB"
     */
    val name: String
    /**
     * Assigned and effective roles of the organization.
     * @example [["write_user", "read_user", "write_organization", "read_organization"]]
     */
    val roles: List<String>
}

data class OrganizationRef(
    override val id: OrganizationId,
    override val name: String,
    override val roles: List<String>
): OrganizationRefDTO
