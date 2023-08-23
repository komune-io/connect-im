package city.smartb.im.privilege.domain

import city.smartb.im.privilege.domain.role.command.RoleCreateFunction
import city.smartb.im.privilege.domain.role.command.RoleUpdateFunction
import city.smartb.im.privilege.domain.role.query.RoleGetByIdFunction
import city.smartb.im.privilege.domain.role.query.RoleGetByNameFunction

/**
 * @d2 api
 */
interface RoleApi: RoleCommandApi, RoleQueryApi

interface RoleCommandApi {
    /** Create a role */
    fun roleCreate(): RoleCreateFunction
    /** Update a role */
    fun roleUpdate(): RoleUpdateFunction
}

interface RoleQueryApi {
    fun roleGetById(): RoleGetByIdFunction
    fun roleGetByName(): RoleGetByNameFunction
}
