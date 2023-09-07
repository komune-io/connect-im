package city.smartb.im.script.init.config

import city.smartb.im.commons.model.ClientIdentifier

data class ImInitProperties(
    val imMasterClient: ClientCredentials
)

data class ClientCredentials(
    val clientId: ClientIdentifier,
    val clientSecret: String
)
