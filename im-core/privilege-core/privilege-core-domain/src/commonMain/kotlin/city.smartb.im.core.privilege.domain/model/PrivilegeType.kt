package city.smartb.im.core.privilege.domain.model

/**
 * Type of privilege.
 * @d2 model
 * @order 15
 */
enum class PrivilegeType {
    /**
     * See [RoleDTO][city.smartb.im.f2.privilege.domain.role.model.RoleDTO]
     */
    ROLE,

    /**
     * See [PermissionDTO][city.smartb.im.f2.privilege.domain.permission.model.PermissionDTO]
     */
    PERMISSION
}
