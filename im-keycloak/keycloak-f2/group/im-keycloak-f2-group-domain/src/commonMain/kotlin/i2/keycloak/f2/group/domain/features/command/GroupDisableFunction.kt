package i2.keycloak.f2.group.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.group.domain.model.GroupId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias GroupDisableFunction = F2Function<GroupDisableCommand, GroupDisabledEvent>

@JsExport
@JsName("GroupDisableCommand")
class GroupDisableCommand(
    val id: GroupId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("GroupDisabledEvent")
class GroupDisabledEvent(
	val id: GroupId
): Event
