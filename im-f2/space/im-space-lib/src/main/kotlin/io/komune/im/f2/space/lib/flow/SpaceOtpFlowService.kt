package io.komune.im.f2.space.lib.flow

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
        val keycloakClient = keycloakClientProvider.get(space)
        val authFlowsClient = keycloakClient.keycloak.realm(space).flows()
        return create(authFlowsClient)
    }

    /*
     *   Browser Flow (Top-Level Flow)
     */
    private fun create(authFlowsClient: AuthenticationManagementResource) {
        ImMfaPasswordOtpFlow.flow.deploy(authFlowsClient)
    }

    suspend fun setAsDefault(keycloakClientProvider: KeycloakClientProvider, space: String) {
        val keycloakClient = keycloakClientProvider.get(space)
        val realmResource = keycloakClient.keycloak.realm(space)

        val realmRepresentation = realmResource.toRepresentation()
        realmRepresentation.browserFlow = ImMfaPasswordOtpFlow.name
        realmResource.update(realmRepresentation)
    }
}
