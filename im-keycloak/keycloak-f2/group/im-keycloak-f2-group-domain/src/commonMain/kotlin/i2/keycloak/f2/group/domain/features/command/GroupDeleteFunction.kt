package i2.keycloak.f2.group.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.group.domain.model.GroupId

typealias GroupDeleteFunction = F2Function<GroupDeleteCommand, GroupDeletedEvent>

class GroupDeleteCommand(
    val id: GroupId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

class GroupDeletedEvent(
	val id: GroupId
): Event
