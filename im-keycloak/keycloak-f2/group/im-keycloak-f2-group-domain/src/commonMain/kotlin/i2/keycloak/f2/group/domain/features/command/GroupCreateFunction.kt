package i2.keycloak.f2.group.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias GroupCreateFunction = F2Function<GroupCreateCommand, GroupCreatedEvent>

@JsExport
@JsName("GroupCreateCommand")
class GroupCreateCommand(
    val name: String,
    val attributes: Map<String, String>,
    val roles: List<String>,
    override val auth: AuthRealm,
    val realmId: RealmId,
    val parentGroupId: GroupId?,
): KeycloakF2Command

@JsExport
@JsName("GroupCreatedEvent")
class GroupCreatedEvent(
	val id: GroupId
): KeycloakF2Result
