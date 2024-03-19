package io.komune.im.apikey.domain.model

import io.komune.im.commons.model.RoleIdentifier

data class ApiKeyModel(
    val id: ApiKeyId,
    val name: String,
    val identifier: ApiKeyIdentifier,
    val roles: List<RoleIdentifier>,
    val creationDate: Long
)
