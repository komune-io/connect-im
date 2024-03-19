package io.komune.im.core.client.domain.command

import io.komune.im.commons.model.ClientId
import kotlinx.serialization.Serializable

@Serializable
data class ClientGrantClientRolesCommand(
    val id: ClientId,
    val providerClientId: ClientId,
    val roles: Collection<String>
)

@Serializable
data class ClientGrantedClientRolesEvent(
    val id: ClientId
)
