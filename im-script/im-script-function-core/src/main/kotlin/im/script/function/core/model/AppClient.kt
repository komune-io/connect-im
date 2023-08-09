package im.script.function.core.model

import i2.keycloak.f2.client.domain.ClientIdentifier

data class AppClient (
    val clientId: ClientIdentifier,
    val clientSecret: String?,
    val roles: List<String>?,
    val realmManagementRoles: List<String>?
)
