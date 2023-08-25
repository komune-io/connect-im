package i2.keycloak.f2.group.domain.features.query

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.group.domain.model.GroupModel

typealias GroupPageFunction = F2Function<GroupPageQuery, GroupPageResult>

class GroupPageQuery(
    val search: String? = null,
    val groupId: GroupId? = null,
    val roles: List<String>? = null,
    val attributes: Map<String, String> = emptyMap(),
    val withDisabled: Boolean,
    val page: PagePagination,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

class GroupPageResult(
	val page: Page<GroupModel>
): KeycloakF2Result
