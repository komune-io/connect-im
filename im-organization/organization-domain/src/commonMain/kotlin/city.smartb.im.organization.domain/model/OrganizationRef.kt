package city.smartb.im.organization.domain.model

/**
 * Short representation of an organization.
 * @D2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 30
 */
data class OrganizationRef(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId,

    /**
     * Name of the organization.
     * @example [Organization.name]
     */
    val name: String,

    /**
     * Assigned and effective roles of the organization.
     * @example [Organization.roles]
     */
    val roles: List<String>
)
