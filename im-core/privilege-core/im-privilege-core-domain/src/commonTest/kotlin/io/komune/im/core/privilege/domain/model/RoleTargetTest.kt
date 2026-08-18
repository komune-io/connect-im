package io.komune.im.core.privilege.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Role targets are persisted on roles in Keycloak and compared by name, so the set and
 * the spelling are both storage format.
 */
class RoleTargetTest {

    @Test
    fun coversEveryEntityRolesCanApplyTo() {
        assertEquals(
            setOf("ORGANIZATION", "USER", "API_KEY"),
            RoleTarget.entries.map { it.name }.toSet()
        )
    }

    @Test
    fun apiKeyKeepsItsUnderscoreSpelling() {
        assertEquals("API_KEY", RoleTarget.API_KEY.name)
    }

    @Test
    fun valueOfRoundTripsForEveryEntry() {
        RoleTarget.entries.forEach { assertEquals(it, RoleTarget.valueOf(it.name)) }
    }

    @Test
    fun privilegeTypeCoversRolePermissionAndFeature() {
        assertTrue(PrivilegeType.entries.map { it.name }.containsAll(listOf("ROLE", "PERMISSION", "FEATURE")))
    }
}
