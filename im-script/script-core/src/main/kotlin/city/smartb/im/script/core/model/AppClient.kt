package city.smartb.im.script.core.model

import city.smartb.im.commons.model.ClientIdentifier

data class AppClient(
    val clientId: ClientIdentifier,
    val clientSecret: String?,
    val roles: List<String>?,
    val realmManagementRoles: List<String>?
)
