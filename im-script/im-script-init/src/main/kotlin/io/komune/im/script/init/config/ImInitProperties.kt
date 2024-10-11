package io.komune.im.script.init.config

import io.komune.im.script.core.model.ClientCredentials

data class ImInitProperties(
    val rootClient: ClientCredentials?,
    @Deprecated("Use rootClient instead")
    val imMasterClient: ClientCredentials?
)
