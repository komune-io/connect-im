package i2.keycloak.f2.realm.domain.features.query

import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.realm.domain.RealmModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmGetFunction = F2Function<RealmGetQuery, RealmGetResult>

@JsExport
@JsName("RealmGetQuery")
class RealmGetQuery(
    val id: RealmId,
    val authRealm: AuthRealm
): Command

@JsExport
@JsName("RealmGetResult")
class RealmGetResult(
	val item: RealmModel?
): Event
