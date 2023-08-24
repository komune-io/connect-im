package i2.test.bdd.data.user

import city.smartb.im.commons.model.AuthRealm
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.userCreateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    username: String = "user-{${UUID.randomUUID()}}",
    firstname: String? = null,
    lastname: String? = null,
    email: String = "$username@mail.com",
    isEnable: Boolean = true,
    isEmailVerified: Boolean = true,
    attributes: Map<String, String> = emptyMap(),
) = UserCreateCommand(
     realmId = realmId,
     username = username,
     firstname = firstname,
     lastname = lastname,
     email = email,
     isEnable = isEnable,
     isEmailVerified = isEmailVerified,
     attributes = attributes,
     auth = auth,
)
