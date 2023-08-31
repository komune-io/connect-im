package im.script.function.core.model

import city.smartb.im.core.client.domain.model.ClientIdentifier

data class WebClient(
    val clientId: ClientIdentifier,
    val webUrl: String
)
