package io.komune.im.commons.auth

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Permission identifiers are compared against role permissions stored in Keycloak.
 * Renaming one silently denies access rather than failing at startup.
 */
class ImPermissionTest {

    @Test
    fun identifiersAreStable() {
        assertEquals("im_user_read", ImPermission.IM_USER_READ.identifier)
        assertEquals("im_user_write", ImPermission.IM_USER_WRITE.identifier)
        assertEquals("im_organization_read", ImPermission.IM_ORGANIZATION_READ.identifier)
        assertEquals("im_organization_write_own", ImPermission.IM_MY_ORGANIZATION_WRITE.identifier)
        assertEquals("im_apikey_write", ImPermission.IM_APIKEY_WRITE.identifier)
        assertEquals("im_space_write", ImPermission.IM_SPACE_WRITE.identifier)
        assertEquals("im_role_write", ImPermission.IM_ROLE_WRITE.identifier)
        assertEquals("im_mfa_force_otp", ImPermission.IM_FORCE_MFA_OTP.identifier)
    }

    @Test
    fun everyIdentifierIsPrefixedWithIm() {
        ImPermission.entries.forEach {
            assertTrue(it.identifier.startsWith("im_"), "${it.name} -> ${it.identifier}")
        }
    }

    @Test
    fun identifiersAreLowerSnakeCase() {
        val shape = Regex("^[a-z0-9_]+$")

        ImPermission.entries.forEach {
            assertTrue(shape.matches(it.identifier), "${it.name} -> ${it.identifier}")
        }
    }

    @Test
    fun imUserRoleReadStillMapsToTheWriteIdentifier() {
        // Looks like a typo, but it is the identifier stored in existing realms.
        // Pinned so that "fixing" it is a deliberate migration, not a silent rename.
        assertEquals("im_user_role_write", ImPermission.IM_USER_ROLE_READ.identifier)
    }
}
