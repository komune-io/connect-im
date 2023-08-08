package i2.init.api.auth.config

import i2.keycloak.f2.client.domain.ClientIdentifier

data class KeycloakInitProperties(
    val realm: String,
    val smtp: KeycloakSmtpConfig?,
    val theme: String?,
    val adminClient: List<AppClient>,
    val baseRoles: List<String>
)

data class KeycloakSmtpConfig(
    val host: String,
    val port: Int,
    val from: String,
    val ssl: Boolean,
    val starttls: Boolean,
    val auth: Boolean
)

// TODO Use class than keyclock config
data class AppClient (
    val clientId: ClientIdentifier,
    val clientSecret: String?,
    val roles: Array<String>?,
    val realmManagementRoles: Array<String>?
)
