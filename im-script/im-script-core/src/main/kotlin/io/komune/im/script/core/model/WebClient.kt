package io.komune.im.script.core.model

import io.komune.im.commons.model.ClientIdentifier

data class WebClient(
    val clientId: ClientIdentifier,
    val webUrl: String
)
