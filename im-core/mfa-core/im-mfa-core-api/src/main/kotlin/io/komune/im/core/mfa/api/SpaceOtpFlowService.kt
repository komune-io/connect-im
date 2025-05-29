package io.komune.im.core.mfa.api

import io.komune.im.core.mfa.domain.model.ImMfaPasswordOtpFlow
import io.komune.im.core.mfa.domain.model.ImResetPasswordFlow
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import org.keycloak.admin.client.resource.AuthenticationManagementResource
import org.springframework.stereotype.Service

@Service
class SpaceOtpFlowService {

    companion object {
        const val OTP_FLOW_USER_ATTRIBUTE = "mfa"
        const val OTP_FLOW_USER_ATTRIBUTE_VALUE = "OTP"
    }

    suspend fun create(keycloakClientProvider: KeycloakClientProvider, space: String) {
        val keycloakClient = keycloakClientProvider.getClient(space)
        val authFlowsClient = keycloakClient.keycloak.realm(space).flows()
        return create(authFlowsClient)
    }

    /*
     *   Browser Flow (Top-Level Flow)
     */
    private fun create(authFlowsClient: AuthenticationManagementResource) {
        ImMfaPasswordOtpFlow.flow.deploy(authFlowsClient)
        ImResetPasswordFlow.flow.deploy(authFlowsClient)
    }

    suspend fun setAsDefault(keycloakClientProvider: KeycloakClientProvider, space: String) {
        val keycloakClient = keycloakClientProvider.getClient(space)
        val realmResource = keycloakClient.keycloak.realm(space)

        val realmRepresentation = realmResource.toRepresentation()
        realmRepresentation.browserFlow = ImMfaPasswordOtpFlow.name
        realmRepresentation.resetCredentialsFlow = ImResetPasswordFlow.name
        realmResource.update(realmRepresentation)
    }
}
