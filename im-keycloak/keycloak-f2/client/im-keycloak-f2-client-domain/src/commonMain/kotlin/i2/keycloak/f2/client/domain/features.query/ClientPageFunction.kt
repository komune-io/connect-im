package i2.keycloak.f2.client.domain.features.query

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientPageFunction = F2Function<ClientPageQuery, ClientPageResult>

@JsExport
@JsName("ClientPageQuery")
class ClientPageQuery(
    val realmId: RealmId,
    val page: PagePagination,
    override val auth: AuthRealm,
): KeycloakF2Query

@JsExport
@JsName("ClientPageResult")
class ClientPageResult(
	val page: Page<ClientModel>
): Event
