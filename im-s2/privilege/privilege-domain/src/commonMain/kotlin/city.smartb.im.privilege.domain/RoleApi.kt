package city.smartb.im.privilege.domain

import city.smartb.im.privilege.domain.role.command.RoleDefineFunction

/**
 * @d2 api
 */
interface RoleApi: RoleCommandApi, RoleQueryApi

interface RoleCommandApi {
    /** Create or update a role */
    fun roleDefine(): RoleDefineFunction
}

interface RoleQueryApi {
}
