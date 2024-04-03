package io.komune.im.script.init.config

import io.komune.im.commons.model.ClientIdentifier

data class ImInitProperties(
    val imMasterClient: ClientCredentials
)

data class ClientCredentials(
    val clientId: ClientIdentifier,
    val clientSecret: String
)
