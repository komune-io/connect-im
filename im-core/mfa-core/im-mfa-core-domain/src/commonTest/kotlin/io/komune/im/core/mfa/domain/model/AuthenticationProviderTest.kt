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
        assertEquals(expected = "auth-username-password-form", actual = AuthenticationProvider.USERNAME_PASSWORD.id)
        assertEquals(expected = "auth-cookie", actual = AuthenticationProvider.COOKIE.id)
        assertEquals(expected = "auth-otp-form", actual = AuthenticationProvider.OTP_FORM.id)
        assertEquals(expected = "reset-password", actual = AuthenticationProvider.RESET_PASSWORD.id)
        assertEquals(
            expected = "reset-credentials-choose-user",
            actual = AuthenticationProvider.RESET_CREDENTIALS_CHOOSE_USER.id
        )
        assertEquals(expected = "reset-credential-email", actual = AuthenticationProvider.RESET_CREDENTIALS_EMAIL.id)
        assertEquals(expected = "identity-provider-redirector", actual = AuthenticationProvider.IDP_REDIRECT.id)
    }

    @Test
    fun basicFlowIsKeycloaksFlowTypeId() {
        assertEquals(expected = "basic-flow", actual = FlowType.BASIC_FLOW.id)
    }

    @Test
    fun requirementsCoverKeycloaksThreeStates() {
        assertEquals(
            expected = setOf("REQUIRED", "ALTERNATIVE", "CONDITIONAL"),
            actual = Requirement.entries.map { it.name }.toSet()
        )
    }

    @Test
    fun providerIdsAreUnique() {
        val ids = AuthenticationProvider.entries.map { it.id }

        assertEquals(expected = ids.size, actual = ids.toSet().size)
    }

    @Test
    fun providerIdsAreKebabCase() {
        val shape = Regex("^[a-z0-9-]+$")

        AuthenticationProvider.entries.forEach {
            assertTrue(shape.matches(it.id), "${it.name} -> ${it.id}")
        }
    }
}
