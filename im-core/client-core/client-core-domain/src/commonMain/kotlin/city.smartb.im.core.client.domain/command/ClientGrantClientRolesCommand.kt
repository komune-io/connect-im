package city.smartb.im.core.client.domain.command

import city.smartb.im.commons.model.ClientId
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
