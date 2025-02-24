package io.komune.im.script.space.config.config

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.script.core.config.properties.SpaceSettingsProperties
import io.komune.im.script.core.model.AppClient
import io.komune.im.script.core.model.FeatureData
import io.komune.im.script.core.model.PermissionData
import io.komune.im.script.core.model.RoleData
import io.komune.im.script.core.model.WebClient

data class SpaceConfigProperties(
    val space: SpaceIdentifier,
    val settings: SpaceSettingsProperties? = null,
    val theme: String? = null,
    val locales: List<String>? = null,
    val mfa: List<String>? = null,
    val appClients: List<AppClient>? = null,

    val webClients: List<WebClient>? = null,
    val features: List<FeatureData>? = null,
    val permissions: List<PermissionData>? = null,
    val roles: List<RoleData>? = null,
    val organizations: List<OrganizationData>? = null,
    val users: List<UserData>? = null,
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
    val siret: String? = null,
    val name: String,
    val description: String? = null,
    val address: Address? = null,
    val roles: List<RoleIdentifier>? = null,
    val attributes: Map<String, String>? = null,
    val users: List<UserData>? = null,
    val apiKeys: List<ApiKeyData>? = null
)

data class ApiKeyData(
    val name: String,
    val secret: String? = null,
    val roles: List<RoleIdentifier>? = null
)
