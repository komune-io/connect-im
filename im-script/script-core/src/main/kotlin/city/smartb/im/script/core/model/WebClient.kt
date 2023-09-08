package city.smartb.im.script.core.model

import city.smartb.im.commons.model.ClientIdentifier

data class WebClient(
    val clientId: ClientIdentifier,
    val webUrl: String
)
