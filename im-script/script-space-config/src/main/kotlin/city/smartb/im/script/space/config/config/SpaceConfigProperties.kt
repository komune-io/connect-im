package city.smartb.im.script.space.config.config

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.SpaceIdentifier
import city.smartb.im.script.core.model.AppClient
import city.smartb.im.script.core.model.PermissionData
import city.smartb.im.script.core.model.RoleData
import city.smartb.im.script.core.model.WebClient

data class SpaceConfigProperties(
    val space: SpaceIdentifier,
    val theme: String?,
    val locales: List<String>?,
    val appClients: List<AppClient>,
    val webClients: List<WebClient>,
    val permissions: List<PermissionData>?,
    val roles: List<RoleData>?,
    val organizations: List<OrganizationData>?,
    val users: List<UserData>?
)

data class UserData(
    val email: String,
    val password: String?,
    val firstname: String,
    val lastname: String,
    val roles: List<RoleIdentifier>,
    val attributes: Map<String, String>?
)

data class OrganizationData(
    val siret: String?,
    val name: String,
    val description: String?,
    val address: Address?,
    val roles: List<RoleIdentifier>?,
    val attributes: Map<String, String>?,
    val users: List<UserData>?,
    val apiKeys: List<ApiKeyData>?
)

data class ApiKeyData(
    val name: String,
    val roles: List<RoleIdentifier>?
)
