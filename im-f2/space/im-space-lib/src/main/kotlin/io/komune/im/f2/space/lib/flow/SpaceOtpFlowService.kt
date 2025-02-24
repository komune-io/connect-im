package io.komune.im.f2.space.lib.flow

import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import org.keycloak.admin.client.resource.AuthenticationManagementResource
import org.springframework.stereotype.Service

@Service
class SpaceOtpFlowService {
    companion object {
        const val OTP_FLOW_NAME = "browser-with-conditional-otp"
        const val OTP_FLOW_USER_ATTRIBUTE = "mfa"
        const val OTP_FLOW_USER_ATTRIBUTE_VALUE = "OTF"
    }

    suspend fun create(keycloakClientProvider: KeycloakClientProvider, space: String) {
        val keycloakClient = keycloakClientProvider.get(space)
        val authFlowsClient = keycloakClient.keycloak.realm(space).flows()
        return create(authFlowsClient)
    }

    /*
     *   Browser Flow (Top-Level Flow)
     *   ├──
     */
    private fun create(authFlowsClient: AuthenticationManagementResource) {
        val browserFlow = authenticationFlow("browser-with-conditional-otp") {
            description = "Custom browser flow with conditional OTP"
            type = FlowType.BASIC_FLOW
            isBuiltIn = false
            isTopLevel = true

            execution(AuthenticationProvider.COOKIE, Requirement.ALTERNATIVE)
            execution(AuthenticationProvider.IDP_REDIRECT, Requirement.ALTERNATIVE)

            subFlow("browser-with-conditional-otp-forms", FlowType.BASIC_FLOW, AuthenticationProvider.FORM_FLOW) {
                execution(AuthenticationProvider.USERNAME_PASSWORD, Requirement.REQUIRED)

                subFlow("browser-with-conditional-otp-otp", FlowType.BASIC_FLOW, AuthenticationProvider.FORM_FLOW) {
                    requirement = Requirement.CONDITIONAL

                    condition(AuthenticationProvider.CONDITIONAL_USER_ATTRIBUTE) {
                        config(
                            "attribute_name" to OTP_FLOW_USER_ATTRIBUTE,
                            "attribute_expected_value" to OTP_FLOW_USER_ATTRIBUTE_VALUE
                        )
                    }
                    execution(AuthenticationProvider.OTP_FORM, Requirement.REQUIRED)
                }
            }
        }.deploy(authFlowsClient)
    }

    suspend fun setAsDefault(keycloakClientProvider: KeycloakClientProvider, space: String) {
        val keycloakClient = keycloakClientProvider.get(space)
        val realmResource = keycloakClient.keycloak.realm(space)

        val realmRepresentation = realmResource.toRepresentation()
        realmRepresentation.browserFlow = OTP_FLOW_NAME
        realmResource.update(realmRepresentation)
    }
}
