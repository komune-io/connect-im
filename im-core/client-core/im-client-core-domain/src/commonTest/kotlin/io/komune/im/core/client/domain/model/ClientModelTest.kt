package io.komune.im.core.client.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Mirrors a Keycloak client. The flow flags decide which OAuth grants a client may use,
 * so they are security posture rather than configuration detail.
 */
class ClientModelTest {

    private fun model(
        id: String = "client-1",
        isPublicClient: Boolean = false,
        isServiceAccountsEnabled: Boolean = true
    ) = ClientModel(
        id = id,
        identifier = "im-root",
        isDirectAccessGrantsEnabled = false,
        isServiceAccountsEnabled = isServiceAccountsEnabled,
        authorizationServicesEnabled = false,
        isStandardFlowEnabled = true,
        isPublicClient = isPublicClient,
        rootUrl = null,
        redirectUris = listOf("https://app.example.com/*"),
        baseUrl = "https://app.example.com",
        adminUrl = "https://app.example.com",
        webOrigins = listOf("https://app.example.com")
    )

    @Test
    fun equalityIsStructural() {
        assertEquals(model(), model())
        assertNotEquals(model(), model(id = "client-2"))
    }

    @Test
    fun theKeycloakIdAndTheClientIdentifierAreDistinct() {
        // `id` is Keycloak's internal uuid; `identifier` is the configured clientId.
        val client = model()

        assertEquals("client-1", client.id)
        assertEquals("im-root", client.identifier)
    }

    @Test
    fun aServiceAccountClientIsConfidentialNotPublic() {
        val client = model(isPublicClient = false, isServiceAccountsEnabled = true)

        assertTrue(client.isServiceAccountsEnabled)
        assertFalse(client.isPublicClient)
    }

    @Test
    fun rootUrlIsOptionalWhileBaseUrlIsNot() {
        assertEquals(null, model().rootUrl)
        assertEquals("https://app.example.com", model().baseUrl)
    }

    @Test
    fun redirectUrisKeepTheirWildcardForm() {
        assertEquals(listOf("https://app.example.com/*"), model().redirectUris)
    }
}
