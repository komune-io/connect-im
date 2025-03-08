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
import org.slf4j.LoggerFactory

data class SpaceConfigProperties(
    @Deprecated("Use identifier instead")
    val space: SpaceIdentifier? = null,
    val identifier: String? = null,
    val displayName: String? = null,
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
) {
    private val logger = LoggerFactory.getLogger(SpaceConfigProperties::class.java)
    val spaceIdentifier: SpaceIdentifier
        get() {
            if(identifier != null) {
                return identifier
            }
            else if (space != null) {
                logger.warn("The 'space' property is deprecated. Use 'identifier' instead.")
                return space
            }
            throw IllegalStateException("Identifier is required")
        }
}

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
