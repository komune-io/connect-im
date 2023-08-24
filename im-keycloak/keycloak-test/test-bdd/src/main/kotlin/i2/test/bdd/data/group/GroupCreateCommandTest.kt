package i2.test.bdd.data.group

import city.smartb.im.commons.model.AuthRealm
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.realm.domain.RealmId
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.groupCreateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    name: String = "group-{${UUID.randomUUID()}}",
    roles: List<String> = emptyList(),
    attributes: Map<String, String> = emptyMap(),
    parentGroupId: GroupId? = null
) = GroupCreateCommand(
    realmId = realmId,
    auth = auth,
    name = name,
    roles = roles,
    attributes = attributes,
    parentGroupId = parentGroupId
)
