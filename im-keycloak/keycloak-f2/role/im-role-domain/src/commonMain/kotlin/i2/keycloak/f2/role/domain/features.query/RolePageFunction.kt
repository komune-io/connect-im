package i2.keycloak.f2.role.domain.features.query

import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RolePageFunction = F2Function<RolePageQuery, RolePageResult>

@JsExport
@JsName("RolePageQuery")
class RolePageQuery(
    val realmId: RealmId,
    val auth: AuthRealm,
    val page: PagePagination,
)

@JsExport
@JsName("RolePageResult")
class RolePageResult(
    val page: Page<RoleModel>
)
