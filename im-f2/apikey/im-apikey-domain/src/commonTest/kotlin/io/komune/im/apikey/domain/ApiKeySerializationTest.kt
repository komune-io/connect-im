package io.komune.im.apikey.domain

import io.komune.im.apikey.domain.model.ApiKey
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json

class ApiKeySerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val apiKey = ApiKey(
        id = "key-1",
        name = "CI key",
        identifier = "ci-key",
        roles = emptyList(),
        creationDate = 1656938975000
    )

    @Test
    fun roundTripsWithoutLoss() {
        val encoded = json.encodeToString(ApiKey.serializer(), apiKey)

        assertEquals(apiKey, json.decodeFromString(ApiKey.serializer(), encoded))
    }

    @Test
    fun keepsItsPublishedFieldNames() {
        val encoded = json.encodeToString(ApiKey.serializer(), apiKey)

        listOf("\"id\"", "\"name\"", "\"identifier\"", "\"roles\"", "\"creationDate\"")
            .forEach { assertTrue(encoded.contains(it), "missing $it in $encoded") }
    }

    @Test
    fun theSecretIsNotPartOfThisModel() {
        // ApiKey is returned by read endpoints; the secret must never ride along.
        val encoded = json.encodeToString(ApiKey.serializer(), apiKey)

        assertTrue(!encoded.contains("secret"), encoded)
    }

    @Test
    fun creationDateIsEpochMillis() {
        val encoded = json.encodeToString(ApiKey.serializer(), apiKey)

        assertTrue(encoded.contains("\"creationDate\":1656938975000"), encoded)
    }
}
