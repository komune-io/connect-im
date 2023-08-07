package i2.test.bdd.data.user

import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.user.domain.features.command.UserUpdateCommand
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.userUpdateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    userId: UserId = UUID.randomUUID().toString(),
    firstname: String? = null,
    lastname: String? = null,
    attributes: Map<String, String> = emptyMap(),
) = UserUpdateCommand(
    userId = userId,
    realmId = realmId,
    firstname = firstname,
    lastname = lastname,
    attributes = attributes,
    auth = auth,
)
