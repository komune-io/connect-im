package i2.keycloak.f2.role.domain

import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleId = String

typealias RoleName = String

@JsExport
@JsName("Role")
class RoleModel(
    val id: RoleId,

    val name: RoleName,

    val description: String?,

    val isClientRole: Boolean,
)
