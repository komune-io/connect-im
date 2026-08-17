package io.komune.im.f2.privilege.domain

import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.f2.privilege.domain.role.model.Role
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json

/**
 * Roles carry the permission model to every consumer.
 */
class RoleSerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val role = Role(
        id = "role-1",
        identifier = "tr_orchestrator_admin",
        description = "Admin role",
        targets = listOf("USER", "API_KEY"),
        locale = mapOf("en" to "Admin", "fr" to "Admin"),
        bindings = emptyMap(),
        permissions = listOf("im_user_read", "im_user_write")
    )

    @Test
    fun roundTripsWithoutLoss() {
        val encoded = json.encodeToString(Role.serializer(), role)

        assertEquals(role, json.decodeFromString(Role.serializer(), encoded))
    }

    @Test
    fun typeIsROLEInMemoryButIsNotSerialized() {
        // `type` is declared in the class body rather than the constructor, so kotlinx
        // leaves it out of the JSON entirely. A consumer discriminating privileges by a
        // `type` field on the wire will not find one here.
        assertEquals(PrivilegeType.ROLE.name, role.type)

        val encoded = json.encodeToString(Role.serializer(), role)

        assertTrue(!encoded.contains("\"type\""), encoded)
    }

    @Test
    fun keepsItsPublishedFieldNames() {
        val encoded = json.encodeToString(Role.serializer(), role)

        listOf("\"id\"", "\"identifier\"", "\"description\"", "\"targets\"", "\"locale\"", "\"permissions\"")
            .forEach { assertTrue(encoded.contains(it), "missing $it in $encoded") }
    }

    @Test
    fun nestedRoleBindingsRoundTrip() {
        val withBindings = role.copy(bindings = mapOf("ORGANIZATION" to listOf(role)))

        val decoded = json.decodeFromString(
            Role.serializer(),
            json.encodeToString(Role.serializer(), withBindings)
        )

        assertEquals(1, decoded.bindings["ORGANIZATION"]?.size)
        assertEquals("tr_orchestrator_admin", decoded.bindings["ORGANIZATION"]?.first()?.identifier)
    }

    @Test
    fun aRoleWithNoPermissionsRoundTrips() {
        val decoded = json.decodeFromString(
            Role.serializer(),
            json.encodeToString(Role.serializer(), role.copy(permissions = emptyList()))
        )

        assertEquals(emptyList<String>(), decoded.permissions)
    }
}
