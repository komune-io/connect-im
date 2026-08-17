package io.komune.im.f2.user.domain

import io.komune.im.commons.model.Address
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.user.domain.model.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json

/**
 * `User` is published API: it is consumed by connect-admin and by Trace over the wire,
 * and exported to TypeScript. Field names and nullability here are a contract.
 */
class UserSerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val user = User(
        id = "user-1",
        memberOf = OrganizationRef(id = "org-1", name = "Komune", roles = emptyList()),
        email = "john.deuf@komune.io",
        givenName = "John",
        familyName = "Deuf",
        address = Address(street = "2 Rue du pavillon", postalCode = "34090", city = "Montpellier"),
        phone = "0612345678",
        roles = emptyList(),
        attributes = mapOf("age" to "42"),
        enabled = true,
        disabledBy = null,
        mfa = listOf("OTP"),
        creationDate = 1656938975000,
        disabledDate = null
    )

    @Test
    fun roundTripsWithoutLoss() {
        val encoded = json.encodeToString(User.serializer(), user)

        assertEquals(user, json.decodeFromString(User.serializer(), encoded))
    }

    @Test
    fun keepsItsPublishedFieldNames() {
        val encoded = json.encodeToString(User.serializer(), user)

        listOf(
            "\"id\"", "\"memberOf\"", "\"email\"", "\"givenName\"", "\"familyName\"",
            "\"address\"", "\"phone\"", "\"roles\"", "\"attributes\"", "\"enabled\"",
            "\"creationDate\""
        ).forEach { assertTrue(encoded.contains(it), "missing $it in $encoded") }
    }

    @Test
    fun nullableFieldsSurviveAsNull() {
        val minimal = user.copy(
            memberOf = null, address = null, phone = null, disabledBy = null,
            mfa = null, disabledDate = null
        )

        val decoded = json.decodeFromString(
            User.serializer(),
            json.encodeToString(User.serializer(), minimal)
        )

        assertEquals(null, decoded.memberOf)
        assertEquals(null, decoded.address)
        assertEquals(null, decoded.phone)
        assertEquals(null, decoded.mfa)
        assertEquals(null, decoded.disabledDate)
    }

    @Test
    fun datesAreEpochMillisNotFormattedStrings() {
        val encoded = json.encodeToString(User.serializer(), user)

        assertTrue(encoded.contains("\"creationDate\":1656938975000"), encoded)
    }

    @Test
    fun anEmptyAttributeMapRoundTrips() {
        val decoded = json.decodeFromString(
            User.serializer(),
            json.encodeToString(User.serializer(), user.copy(attributes = emptyMap()))
        )

        assertEquals(emptyMap<String, String>(), decoded.attributes)
    }
}
