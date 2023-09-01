package city.smartb.im.script.space.config.config

import city.smartb.im.commons.model.RealmId
import city.smartb.im.script.core.model.AppClient
import city.smartb.im.script.core.model.PermissionData
import city.smartb.im.script.core.model.RoleData
import city.smartb.im.script.core.model.WebClient

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
