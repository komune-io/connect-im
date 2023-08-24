package i2.keycloak.f2.client.domain.features.query

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetFunction = F2Function<ClientGetQuery, ClientGetResult>

@JsExport
@JsName("ClientGetQuery")
class ClientGetQuery(
    val id: ClientId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Query

@JsExport
@JsName("ClientGetResult")
class ClientGetResult(
	val item: ClientModel?
): Event
