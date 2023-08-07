package i2.keycloak.f2.group.domain.features.command

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias GroupSetAttributesFunction = F2Function<GroupSetAttributesCommand, GroupSetAttributesEvent>

@JsExport
@JsName("GroupSetAttributesCommand")
class GroupSetAttributesCommand(
    val id: GroupId,
    val attributes: Map<String, String?>,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("GroupSetAttributesEvent")
class GroupSetAttributesEvent(
	val id: GroupId
): Event
