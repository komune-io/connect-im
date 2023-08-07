package i2.keycloak.f2.user.domain.model

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("UserGroup")
class UserGroup(
    val id: String,
    val name: String,
    val roles: List<String>
)
