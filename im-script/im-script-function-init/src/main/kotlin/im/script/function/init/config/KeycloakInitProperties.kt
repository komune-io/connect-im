package im.script.function.init.config

import im.script.function.core.model.AppClient

data class KeycloakInitProperties(
    val realm: String,
    val theme: String?,
    val smtp: Map<String, String>?,
    val adminUser: List<AdminUserData>,
    val adminClient: List<AppClient>,
    val baseRoles: List<String>
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
