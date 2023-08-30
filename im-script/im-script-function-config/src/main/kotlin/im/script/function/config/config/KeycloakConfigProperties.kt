package im.script.function.config.config

import city.smartb.im.commons.model.RealmId
import im.script.function.core.model.AppClient
import im.script.function.core.model.PermissionData
import im.script.function.core.model.RoleData
import im.script.function.core.model.WebClient

class KeycloakConfigProperties(
    val realmId: RealmId,
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
