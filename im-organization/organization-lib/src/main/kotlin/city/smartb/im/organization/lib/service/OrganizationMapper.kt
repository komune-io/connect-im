package city.smartb.im.organization.lib.service

interface OrganizationMapper<FROM, TO> {

    fun from(model: FROM): TO
    fun to(model: TO): FROM
}
