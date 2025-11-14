package io.komune.im.script.space.create.config

import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.script.core.config.properties.SpaceSettingsProperties
import org.slf4j.LoggerFactory

data class SpaceCreateProperties(
    @Deprecated("Use identifier instead")
    val space: SpaceIdentifier? = null,
    val identifier: String? = null,
    val displayName: String? = null,
    val settings: SpaceSettingsProperties? = null,
    val theme: String? = null,
    val smtp: Map<String, String>? = null,
    val locales: List<String>? = null,
    val mfa: List<String>? = null,
    val adminUsers: List<AdminUserData>? = null,
    val rootClient: ClientCredentials? = null,
    val eventWebhook: EventWebhookConfig? = null
) {
    private val logger = LoggerFactory.getLogger(SpaceCreateProperties::class.java)
    val spaceIdentifier: SpaceIdentifier
        get() {
            if(identifier != null) {
                return identifier
            } else
            if (space != null) {
                logger.warn("The 'space' property is deprecated. Use 'identifier' instead.")
                return space
            }
            throw IllegalStateException("Identifier is required")
        }
}

data class ClientCredentials(
    val clientId: ClientIdentifier?,
    val clientSecret: String
)

data class AdminUserData(
    val email: String,
    val password: String?,
    val username: String?,
    val firstName: String?,
    val lastName: String?
)

data class EventWebhookConfig(
    val url: String,
    val secret: String?,
    val eventTypes: List<String>?
)
