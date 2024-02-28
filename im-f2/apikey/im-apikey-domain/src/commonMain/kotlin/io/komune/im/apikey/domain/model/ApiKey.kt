package io.komune.im.apikey.domain.model

import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.privilege.domain.role.model.RoleDTO
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
    val roles: List<RoleDTO>
    val creationDate: Long
}

data class ApiKey(
    override val id: ApiKeyId,
    override val name: String,
    override val identifier: ApiKeyIdentifier,
    override val roles: List<Role>,
    override val creationDate: Long
): ApiKeyDTO
