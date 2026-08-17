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
    fun typeIsSerializedSoConsumersCanDiscriminatePrivileges() {
        assertEquals(PrivilegeType.ROLE.name, role.type)

        val encoded = json.encodeToString(Role.serializer(), role)

        assertTrue(encoded.contains("\"type\":\"ROLE\""), encoded)
    }

    @Test
    fun typeIsEmittedEvenThoughItIsADefault() {
        // @EncodeDefault(ALWAYS) — without it kotlinx omits defaults and the
        // discriminator would silently vanish from the wire again.
        val encoded = json.encodeToString(Role.serializer(), role)

        assertTrue(encoded.contains("\"type\""), encoded)
    }

    @Test
    fun olderPayloadsWithoutTypeStillDecode() {
        val withoutType = """{"id":"role-1","identifier":"r","description":"d","targets":[],"locale":{},"bindings":{},"permissions":[]}"""

        val decoded = json.decodeFromString(Role.serializer(), withoutType)

        assertEquals(PrivilegeType.ROLE.name, decoded.type)
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
