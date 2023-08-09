package im.script.function.init.config

import i2.keycloak.f2.client.domain.ClientIdentifier

data class KeycloakInitProperties(
    val realm: String,
    val theme: String?,
    val smtp: Map<String, String>?,
    val adminUser: List<AdminUserData>,
    val adminClient: List<AppClient>,
    val baseRoles: List<String>
)

// TODO Use class than keyclock config
data class AppClient (
    val clientId: ClientIdentifier,
    val clientSecret: String?,
    val roles: Array<String>?,
    val realmManagementRoles: Array<String>?
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
