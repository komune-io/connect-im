package i2.keycloak.f2.client.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetServiceAccountFunction = F2Function<ClientGetServiceAccountQuery, ClientGetServiceAccountResult>

@JsExport
@JsName("ClientGetServiceAccountQuery")
class ClientGetServiceAccountQuery(
    val id: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientGetServiceAccountResult")
class ClientGetServiceAccountResult(
	val item: UserModel?
): Event
