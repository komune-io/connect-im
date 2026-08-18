package io.komune.im.f2.space.domain

import io.komune.im.f2.space.domain.model.Space
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json

class SpaceSerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val space = Space(
        identifier = "my-space",
        displayName = "My Space",
        theme = "dark",
        smtp = mapOf("host" to "smtp.example.com"),
        locales = listOf("en", "fr")
    )

    @Test
    fun roundTripsWithoutLoss() {
        val encoded = json.encodeToString(Space.serializer(), space)

        assertEquals(space, json.decodeFromString(Space.serializer(), encoded))
    }

    @Test
    fun everythingButTheIdentifierIsOptional() {
        val bare = Space(
            identifier = "my-space",
            displayName = null,
            theme = null,
            smtp = null,
            locales = null
        )

        val decoded = json.decodeFromString(
            Space.serializer(),
            json.encodeToString(Space.serializer(), bare)
        )

        assertEquals("my-space", decoded.identifier)
        assertEquals(null, decoded.displayName)
        assertEquals(null, decoded.smtp)
    }

    @Test
    fun keepsItsPublishedFieldNames() {
        val encoded = json.encodeToString(Space.serializer(), space)

        listOf("\"identifier\"", "\"displayName\"", "\"theme\"", "\"smtp\"", "\"locales\"")
            .forEach { assertTrue(encoded.contains(it), "missing $it in $encoded") }
    }

    @Test
    fun smtpSettingsSurviveAsAFlatStringMap() {
        val decoded = json.decodeFromString(
            Space.serializer(),
            json.encodeToString(Space.serializer(), space)
        )

        assertEquals("smtp.example.com", decoded.smtp?.get("host"))
    }
}
