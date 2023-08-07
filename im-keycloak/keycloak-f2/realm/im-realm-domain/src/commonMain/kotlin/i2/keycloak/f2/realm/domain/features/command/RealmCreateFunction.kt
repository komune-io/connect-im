package i2.keycloak.f2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmCreateFunction = F2Function<RealmCreateCommand, RealmCreatedEvent>

@JsExport
@JsName("RealmCreateCommand")
class RealmCreateCommand(
	val id: String,
	val theme: String?,
	val locale: String?,
	val smtpServer: Map<String, String>?,
	val masterRealmAuth: AuthRealm
): Command

@JsExport
@JsName("RealmCreatedEvent")
class RealmCreatedEvent(
	val id: String
): Event
