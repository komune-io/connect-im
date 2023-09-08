package city.smartb.im.apikey.domain.model

import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.ClientIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ApiKeyId = ClientId
typealias ApiKeyIdentifier = ClientIdentifier

@JsExport
@JsName("ApiKeyDTO")
interface ApiKeyDTO {
    val id: ApiKeyId
    val name: String
    val identifier: ApiKeyIdentifier
    val creationDate: Long
}

data class ApiKey(
    override val id: ApiKeyId,
    override val name: String,
    override val identifier: ApiKeyIdentifier,
    override val creationDate: Long
): ApiKeyDTO
