package io.komune.im.apikey.domain.model

import io.komune.im.commons.model.RoleIdentifier
import kotlinx.serialization.Serializable

@Serializable
data class ApiKeyModel(
    val id: ApiKeyId,
    val name: String,
    val identifier: ApiKeyIdentifier,
    val roles: List<RoleIdentifier>,
    val creationDate: Long
)
