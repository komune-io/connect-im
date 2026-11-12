package io.komune.im.core.client.domain.command

import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier
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
    val baseUrl: String? = null,
    val adminUrl: String? = null,
    val webOrigins: List<String> = emptyList(),
    val additionalAccessTokenClaim: List<String> = emptyList()
)

data class ClientCreatedEvent(
    val id: ClientId,
    val identifier: ClientIdentifier
)
