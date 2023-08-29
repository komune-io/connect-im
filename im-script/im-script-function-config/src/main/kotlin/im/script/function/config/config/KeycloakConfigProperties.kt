package im.script.function.config.config

import i2.keycloak.f2.client.domain.ClientIdentifier
import im.script.function.core.model.AppClient
import im.script.function.core.model.PermissionData
import im.script.function.core.model.RoleData

class KeycloakConfigProperties (
    val appClients: List<AppClient>,
    val webClients: List<WebClient>,
    val users: List<KeycloakUserConfig>?,
    val permissions: List<PermissionData>?,
    val roles: List<RoleData>?
)

class KeycloakUserConfig(
    val username: String,
    val email: String,
    val password: String?,
    val firstname: String,
    val lastname: String,
    val role: String
)

class WebClient (
    val clientId: ClientIdentifier,
    val webUrl: String
)
