package io.komune.im.script.space.create.config

import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.script.core.config.properties.SpaceSettingsProperties

data class SpaceCreateProperties(
    val space: SpaceIdentifier,
    val settings: SpaceSettingsProperties? = null,
    val theme: String? = null,
    val smtp: Map<String, String>? = null,
    val locales: List<String>? = null,
    val adminUsers: List<AdminUserData>? = null,
    val rootClient: ClientCredentials? = null,
)

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
