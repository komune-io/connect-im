package i2.keycloak.f2.client.domain

import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.ClientIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName



@JsExport
@JsName("ClientModel")
class ClientModel(
    val id: ClientId,
    val clientIdentifier: ClientIdentifier
)
