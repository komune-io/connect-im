package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserPageFunction = F2Function<UserPageQuery, UserPageResult>

@JsExport
@JsName("UserPageQuery")
class UserPageQuery(
    val groupId: String? = null,
    val search: String? = null,
    val roles: List<String>? = null,
    val attributes: Map<String, String> = emptyMap(),
    val withDisabled: Boolean,
    val page: PagePagination,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Query

@JsExport
@JsName("UserPageResult")
class UserPageResult(
	val items: Page<UserModel>
): Event
