package i2.keycloak.f2.client.domain.features.query

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientPageFunction = F2Function<ClientPageQuery, ClientPageResult>

@JsExport
@JsName("ClientPageQuery")
class ClientPageQuery(
    val realmId: RealmId,
    val page: PagePagination,
    val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientPageResult")
class ClientPageResult(
	val page: Page<ClientModel>
): Event
