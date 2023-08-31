package city.smartb.im.core.client.domain.command

import city.smartb.im.core.client.domain.model.ClientId
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
