package im.script.function.init.config

import city.smartb.im.commons.model.RealmId
import city.smartb.im.core.client.domain.model.ClientIdentifier
import im.script.function.core.model.AppClient

data class KeycloakInitProperties(
    val realmId: RealmId,
    val theme: String?,
    val smtp: Map<String, String>?,
    val adminUsers: List<AdminUserData>,
    val imMasterClient: ClientCredentials,
    val adminClients: List<AppClient>
)

data class AdminUserData(
    val email: String,
    val password: String?,
    val username: String?,
    val firstName: String?,
    val lastName: String?
)

data class ClientCredentials(
    val clientId: ClientIdentifier,
    val clientSecret: String
)
