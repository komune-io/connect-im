package i2.keycloak.f2.realm.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.master.domain.AuthRealm
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmListFunction = F2Function<RealmListQuery, RealmListResult>

@JsExport
@JsName("RealmListQuery")
class RealmListQuery(
    val authRealm: AuthRealm
): Query

@JsExport
@JsName("RealmPageResult")
class RealmListResult(
    override val items: List<RealmModel>,
    override val total: Int
): PageDTO<RealmModel>

