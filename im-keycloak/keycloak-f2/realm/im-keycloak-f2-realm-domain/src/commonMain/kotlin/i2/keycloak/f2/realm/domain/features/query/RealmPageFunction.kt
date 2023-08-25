package i2.keycloak.f2.realm.domain.features.query

import city.smartb.im.commons.model.AuthRealm
import f2.dsl.cqrs.Query
import f2.dsl.cqrs.page.PageDTO
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Message
import i2.keycloak.f2.realm.domain.RealmModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmListFunction = F2Function<RealmListQuery, RealmListResult>

@JsExport
@JsName("RealmListQuery")
class RealmListQuery(
    override val auth: AuthRealm
): Query, KeycloakF2Message

@JsExport
@JsName("RealmPageResult")
class RealmListResult(

    override val items: List<RealmModel>,
    override val total: Int,
): PageDTO<RealmModel>

