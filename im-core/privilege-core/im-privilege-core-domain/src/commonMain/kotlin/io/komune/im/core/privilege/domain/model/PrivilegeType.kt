package io.komune.im.core.privilege.domain.model

/**
 * Type of privilege.
 * @d2 model
 * @order 15
 */
enum class PrivilegeType {
    /**
     * See [RoleDTO][io.komune.im.f2.privilege.domain.role.model.RoleDTO]
     */
    ROLE,

    /**
     * See [PermissionDTO][io.komune.im.f2.privilege.domain.permission.model.PermissionDTO]
     */
    PERMISSION
}
