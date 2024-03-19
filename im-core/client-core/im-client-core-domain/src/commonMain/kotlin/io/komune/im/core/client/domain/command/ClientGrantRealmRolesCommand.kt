package io.komune.im.core.client.domain.command

import io.komune.im.commons.model.ClientId
import kotlinx.serialization.Serializable

@Serializable
data class ClientGrantRealmRolesCommand(
    val id: ClientId,
    val roles: Collection<String>
)

@Serializable
data class ClientGrantedRealmRolesEvent(
    val id: ClientId
)
