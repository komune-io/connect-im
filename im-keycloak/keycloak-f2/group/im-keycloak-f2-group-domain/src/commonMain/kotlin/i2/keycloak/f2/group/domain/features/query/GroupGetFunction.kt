package i2.keycloak.f2.group.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.group.domain.model.GroupModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId

typealias GroupGetFunction = F2Function<GroupGetQuery, GroupGetResult>

class GroupGetQuery(
    val id: GroupId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

class GroupGetResult(
	val item: GroupModel?
): KeycloakF2Result
