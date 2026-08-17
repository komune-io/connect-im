package io.komune.im.core.user.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * `UserModel` is the internal shape read back from Keycloak, and `isApiKey` is what keeps
 * service accounts out of user listings.
 */
class UserModelTest {

    private fun model(isApiKey: Boolean = false, memberOf: String? = "org-1") = UserModel(
        id = "user-1",
        memberOf = memberOf,
        email = "john@komune.io",
        givenName = "John",
        familyName = "Deuf",
        roles = listOf("admin"),
        mfa = emptyList(),
        attributes = mapOf("age" to "42"),
        enabled = true,
        disabledBy = null,
        creationDate = 1656938975000,
        disabledDate = null
    , isApiKey = isApiKey)

    @Test
    fun apiKeysAreDistinguishableFromRealUsers() {
        assertTrue(model(isApiKey = true).isApiKey)
        assertFalse(model(isApiKey = false).isApiKey)
    }

    @Test
    fun aUserWithNoOrganizationIsRepresentable() {
        assertEquals(null, model(memberOf = null).memberOf)
    }

    @Test
    fun equalityIsStructural() {
        assertEquals(model(), model())
    }

    @Test
    fun copyPreservesEverythingElse() {
        val disabled = model().copy(enabled = false, disabledBy = "admin-1", disabledDate = 1)

        assertFalse(disabled.enabled)
        assertEquals("admin-1", disabled.disabledBy)
        assertEquals("john@komune.io", disabled.email)
    }
}
