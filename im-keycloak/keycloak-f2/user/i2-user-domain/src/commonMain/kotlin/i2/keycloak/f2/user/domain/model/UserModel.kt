package i2.keycloak.f2.user.domain.model

import i2.keycloak.f2.role.domain.RolesCompositesModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserId = String

@JsExport
@JsName("UserModel")
class UserModel(
    val id: UserId,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val roles: RolesCompositesModel,
    val attributes: Map<String, String>,
    val enabled: Boolean,
    val creationDate: Long
)
