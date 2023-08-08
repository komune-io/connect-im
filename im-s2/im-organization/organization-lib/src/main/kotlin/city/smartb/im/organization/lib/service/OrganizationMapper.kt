package city.smartb.im.organization.lib.service

interface OrganizationMapper<FROM, Organization> {
    fun mapModel(model: FROM): Organization
    fun mapOrganization(model: Organization): FROM
}
