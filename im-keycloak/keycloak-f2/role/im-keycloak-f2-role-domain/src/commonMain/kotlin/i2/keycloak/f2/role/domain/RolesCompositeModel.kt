package i2.keycloak.f2.role.domain

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("RoleCompositesModel")
class RoleCompositesModel(
    val assignedRole: String,
    val effectiveRoles: List<String>
)

@JsExport
@JsName("RolesCompositeModel")
class RolesCompositesModel(
    /**
     * Roles assigned to the organization.
     * @example [["admin"]]
     */
    val assignedRoles: List<String>,
    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [["admin", "write_user", "read_user", "write_organization", "read_organization"]]
     */
    val effectiveRoles: List<String>
)

enum class RoleCompositeObjType {
    USER, GROUP
}
