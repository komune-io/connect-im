package io.komune.im.f2.privilege.domain

import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionGetFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionListFunction

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
    /** Get a list of permissions */
    fun permissionList(): PermissionListFunction
}
