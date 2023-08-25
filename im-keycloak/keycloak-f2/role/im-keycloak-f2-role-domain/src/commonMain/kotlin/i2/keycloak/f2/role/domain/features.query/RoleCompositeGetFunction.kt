package i2.keycloak.f2.role.domain.features.query

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.role.domain.RoleCompositeObjType
import i2.keycloak.f2.role.domain.RolesCompositesModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleCompositeGetFunction = F2Function<RoleCompositeGetQuery, RoleCompositeGetResult>

@JsExport
@JsName("RoleCompositeGetQuery")
class RoleCompositeGetQuery(
    val realmId: RealmId,
    val objId: String,
    val objType: RoleCompositeObjType,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("RoleCompositeGetResult")
class RoleCompositeGetResult(
    val item: RolesCompositesModel
)
