package i2.keycloak.f2.client.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetByClientIdentifierFunction = F2Function<ClientGetByClientIdentifierQuery, ClientGetByClientIdentifierResult>

@JsExport
@JsName("ClientGetByClientIdentifierQuery")
class ClientGetByClientIdentifierQuery(
    val clientIdentifier: ClientIdentifier,
    val realmId: RealmId,
    val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientGetByClientIdentifierResult")
class ClientGetByClientIdentifierResult(
	val item: ClientModel?
): Event
