package i2.keycloak.f2.group.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.group.domain.model.GroupModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId

typealias GroupGetAllQueryFunction = F2Function<GroupGetAllQuery, GroupGetAllQueryResult>

class GroupGetAllQuery(
	val search: String = "",
	val page: Int = 0,
	val size: Int = 1000,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

class GroupGetAllQueryResult(
	val groups: List<GroupModel>
): KeycloakF2Result
