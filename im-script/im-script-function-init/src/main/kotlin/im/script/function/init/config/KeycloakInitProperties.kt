package im.script.function.init.config

import i2.keycloak.f2.realm.domain.RealmId
import im.script.function.core.model.AppClient

data class KeycloakInitProperties(
    val realmId: RealmId,
    val theme: String?,
    val smtp: Map<String, String>?,
    val adminUser: List<AdminUserData>,
    val adminClient: List<AppClient>
)

data class AdminUserData(
    val email: String,
    val password: String?,
    val username: String?,
    val firstName: String?,
    val lastName: String?
)

data class AdminClientData(
    val name: String,
    val secret: String
)
