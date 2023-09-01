package city.smartb.im.script.space.config.config

import city.smartb.im.f2.space.domain.model.SpaceIdentifier
import city.smartb.im.script.core.model.AppClient
import city.smartb.im.script.core.model.PermissionData
import city.smartb.im.script.core.model.RoleData
import city.smartb.im.script.core.model.WebClient

class SpaceConfigProperties(
    val space: SpaceIdentifier,
    val appClients: List<AppClient>,
    val webClients: List<WebClient>,
    val users: List<UserData>?,
    val permissions: List<PermissionData>?,
    val roles: List<RoleData>?
)

class UserData(
    val username: String,
    val email: String,
    val password: String?,
    val firstname: String,
    val lastname: String,
    val role: String
)
