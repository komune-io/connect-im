package io.komune.im.script.core.model

import io.komune.im.commons.model.ClientIdentifier

data class AppClient(
    val clientId: ClientIdentifier,
    val clientSecret: String?,
    val roles: List<String>?,
    val realmManagementRoles: List<String>?,
    /*
    * To be able to redirect to the home page of the client application
    */
    val homeUrl: String? = null,
)
