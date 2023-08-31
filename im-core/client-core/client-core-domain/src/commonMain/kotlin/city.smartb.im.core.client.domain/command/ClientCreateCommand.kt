package city.smartb.im.core.client.domain.command

import city.smartb.im.core.client.domain.model.ClientId
import city.smartb.im.core.client.domain.model.ClientIdentifier
import kotlinx.serialization.Serializable

@Serializable
data class ClientCreateCommand(
    val identifier: ClientIdentifier,
    val secret: String? = null,
    val isPublicClient: Boolean,
    val isDirectAccessGrantsEnabled: Boolean,
    val isServiceAccountsEnabled: Boolean,
    val authorizationServicesEnabled: Boolean,
    val isStandardFlowEnabled: Boolean,
    val rootUrl: String? = null,
    val redirectUris: List<String> = emptyList(),
    val baseUrl: String = "",
    val adminUrl: String = "",
    val webOrigins: List<String> = emptyList(),
    val additionalAccessTokenClaim: List<String> = emptyList()
)

data class ClientCreatedEvent(
    val id: ClientId,
    val identifier: ClientIdentifier
)
