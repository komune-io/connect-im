package city.smartb.im.f2.privilege.domain

import city.smartb.im.f2.privilege.domain.role.command.RoleDefineFunction
import city.smartb.im.f2.privilege.domain.role.query.RoleGetFunction
import city.smartb.im.f2.privilege.domain.role.query.RoleListFunction

/**
 * @d2 api
 * @parent [D2RolePage]
 */
interface RoleApi: RoleCommandApi, RoleQueryApi

interface RoleCommandApi {
    /** Create or update a role */
    fun roleDefine(): RoleDefineFunction
}

interface RoleQueryApi {
    /** Get a role by identifier */
    fun roleGet(): RoleGetFunction
    /** Get a list of roles */
    fun roleList(): RoleListFunction
}
