package io.komune.im.core.mfa.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * These ids are Keycloak's own authenticator provider ids. They are sent verbatim when
 * building authentication flows, so a wrong string produces a realm that fails to
 * authenticate rather than a build error.
 */
class AuthenticationProviderTest {

    @Test
    fun keycloakAuthenticatorIdsAreExact() {
        assertEquals("auth-username-password-form", AuthenticationProvider.USERNAME_PASSWORD.id)
        assertEquals("auth-cookie", AuthenticationProvider.COOKIE.id)
        assertEquals("auth-otp-form", AuthenticationProvider.OTP_FORM.id)
        assertEquals("reset-password", AuthenticationProvider.RESET_PASSWORD.id)
        assertEquals("reset-credentials-choose-user", AuthenticationProvider.RESET_CREDENTIALS_CHOOSE_USER.id)
        assertEquals("reset-credential-email", AuthenticationProvider.RESET_CREDENTIALS_EMAIL.id)
        assertEquals("identity-provider-redirector", AuthenticationProvider.IDP_REDIRECT.id)
    }

    @Test
    fun basicFlowIsKeycloaksFlowTypeId() {
        assertEquals("basic-flow", FlowType.BASIC_FLOW.id)
    }

    @Test
    fun requirementsCoverKeycloaksThreeStates() {
        assertEquals(
            setOf("REQUIRED", "ALTERNATIVE", "CONDITIONAL"),
            Requirement.entries.map { it.name }.toSet()
        )
    }

    @Test
    fun providerIdsAreUnique() {
        val ids = AuthenticationProvider.entries.map { it.id }

        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun providerIdsAreKebabCase() {
        val shape = Regex("^[a-z0-9-]+$")

        AuthenticationProvider.entries.forEach {
            assertTrue(shape.matches(it.id), "${it.name} -> ${it.id}")
        }
    }
}
