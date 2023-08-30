package city.smartb.im.core.client.api.model

import city.smartb.im.core.client.domain.model.Client
import org.keycloak.representations.idm.ClientRepresentation

fun ClientRepresentation.toClient() = Client(
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
