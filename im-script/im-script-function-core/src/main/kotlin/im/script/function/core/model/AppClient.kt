package im.script.function.core.model

import city.smartb.im.core.client.domain.model.ClientIdentifier

data class AppClient(
    val clientId: ClientIdentifier,
    val clientSecret: String?,
    val roles: List<String>?,
    val realmManagementRoles: List<String>?
)
