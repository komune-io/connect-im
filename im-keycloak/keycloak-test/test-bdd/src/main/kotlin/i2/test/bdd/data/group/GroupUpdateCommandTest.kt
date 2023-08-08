package i2.test.bdd.data.group

import i2.keycloak.f2.group.domain.features.command.GroupUpdateCommand
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.master.domain.AuthRealm
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.groupUpdateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    id: GroupId,
    name: String = "group-{${UUID.randomUUID()}}",
    roles: List<String> = emptyList(),
    attributes: Map<String, String> = emptyMap(),
) = GroupUpdateCommand(
    realmId = realmId,
    auth = auth,
    id = id,
    name = name,
    roles = roles,
    attributes = attributes
)
