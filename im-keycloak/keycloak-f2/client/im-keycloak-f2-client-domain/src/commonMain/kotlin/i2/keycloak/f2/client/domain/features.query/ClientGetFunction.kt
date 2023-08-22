package i2.keycloak.f2.client.domain.features.query

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetFunction = F2Function<ClientGetQuery, ClientGetResult>

@JsExport
@JsName("ClientGetQuery")
class ClientGetQuery(
    val id: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientGetResult")
class ClientGetResult(
	val item: ClientModel?
): Event
