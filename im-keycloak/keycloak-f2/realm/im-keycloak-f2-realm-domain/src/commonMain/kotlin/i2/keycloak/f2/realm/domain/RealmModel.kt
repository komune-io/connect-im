package i2.keycloak.f2.realm.domain

import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmId = String

@JsExport
@JsName("RealmModel")
class RealmModel(
    val id: RealmId,
    val name: String?
)
