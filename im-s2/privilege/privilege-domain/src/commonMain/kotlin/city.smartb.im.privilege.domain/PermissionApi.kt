package city.smartb.im.privilege.domain

import city.smartb.im.privilege.domain.permission.command.PermissionDefineFunction
import city.smartb.im.privilege.domain.permission.query.PermissionGetFunction

/**
 * @d2 api
 * @parent [D2PermissionPage]
 */
interface PermissionApi: PermissionCommandApi, PermissionQueryApi

interface PermissionCommandApi {
    /** Create or update a permission */
    fun permissionDefine(): PermissionDefineFunction
}

interface PermissionQueryApi {
    /** Get a permission by identifier */
    fun permissionGet(): PermissionGetFunction
}
