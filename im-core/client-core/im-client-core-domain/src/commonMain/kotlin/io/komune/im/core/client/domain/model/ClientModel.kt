package io.komune.im.core.client.domain.model

import io.komune.im.commons.model.ClientId
import io.komune.im.commons.model.ClientIdentifier

data class ClientModel(
    val id: ClientId,
    val identifier: ClientIdentifier,
    val isDirectAccessGrantsEnabled: Boolean,
    val isServiceAccountsEnabled: Boolean,
    val authorizationServicesEnabled: Boolean,
    val isStandardFlowEnabled: Boolean,
    val isPublicClient: Boolean,
    val rootUrl: String?,
    val redirectUris: List<String>,
    val baseUrl: String,
    val adminUrl: String,
    val webOrigins: List<String>
)
