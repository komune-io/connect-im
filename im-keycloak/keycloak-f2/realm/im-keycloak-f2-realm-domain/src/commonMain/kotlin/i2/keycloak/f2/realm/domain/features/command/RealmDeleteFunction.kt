package i2.keycloak.f2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmDeleteFunction = F2Function<RealmDeleteCommand, RealmDeletedEvent>

@JsExport
@JsName("RealmDeleteCommand")
class RealmDeleteCommand(
	val id: RealmId,
    val masterRealmAuth: AuthRealm
): Command

@JsExport
@JsName("RealmDeletedEvent")
class RealmDeletedEvent(
	val id: RealmId,
): Event
