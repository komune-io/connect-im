package io.komune.im.core.client.api.model

import io.komune.im.core.client.domain.model.ClientModel
import org.keycloak.representations.idm.ClientRepresentation

fun ClientRepresentation.toClient() = ClientModel(
    id = id,
    identifier = clientId,
    isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
    isServiceAccountsEnabled = isServiceAccountsEnabled,
    authorizationServicesEnabled = authorizationServicesEnabled ?: false,
    isStandardFlowEnabled = isStandardFlowEnabled,
    isPublicClient = isPublicClient,
    rootUrl = rootUrl ?: "",
    redirectUris = redirectUris.orEmpty(),
    baseUrl = baseUrl ?: "",
    adminUrl = adminUrl ?: "",
    webOrigins = webOrigins.orEmpty()
)
