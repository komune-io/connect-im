package io.komune.im.f2.organization.domain

import io.komune.im.f2.organization.domain.model.OrganizationRef
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json

/**
 * `OrganizationRef` is embedded in every `User`, so its shape is part of the user API.
 */
class OrganizationRefSerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val ref = OrganizationRef(id = "org-1", name = "Komune", roles = listOf("admin"))

    @Test
    fun roundTripsWithoutLoss() {
        val encoded = json.encodeToString(OrganizationRef.serializer(), ref)

        assertEquals(ref, json.decodeFromString(OrganizationRef.serializer(), encoded))
    }

    @Test
    fun keepsItsPublishedFieldNames() {
        val encoded = json.encodeToString(OrganizationRef.serializer(), ref)

        assertTrue(encoded.contains("\"id\""), encoded)
        assertTrue(encoded.contains("\"name\""), encoded)
        assertTrue(encoded.contains("\"roles\""), encoded)
    }

    @Test
    fun anOrganizationWithNoRolesRoundTrips() {
        val bare = ref.copy(roles = emptyList())

        val decoded = json.decodeFromString(
            OrganizationRef.serializer(),
            json.encodeToString(OrganizationRef.serializer(), bare)
        )

        assertEquals(emptyList<String>(), decoded.roles)
    }

    @Test
    fun toleratesFieldsAddedByANewerServer() {
        val decoded = json.decodeFromString(
            OrganizationRef.serializer(),
            """{"id":"org-1","name":"Komune","roles":[],"somethingNew":true}"""
        )

        assertEquals("org-1", decoded.id)
    }
}
