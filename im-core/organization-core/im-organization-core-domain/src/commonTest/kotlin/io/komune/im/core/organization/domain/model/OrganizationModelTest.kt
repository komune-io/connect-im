package io.komune.im.core.organization.domain.model

import io.komune.im.commons.model.Address
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class OrganizationModelTest {

    private fun model(id: String = "org-1") = OrganizationModel(
        id = id,
        identifier = "komune",
        displayName = "Komune",
        description = null,
        address = Address(street = "2 Rue du pavillon", postalCode = "34090", city = "Montpellier"),
        attributes = mapOf("siret" to "12345678900011"),
        roles = listOf("admin"),
        enabled = true,
        disabledBy = null,
        creationDate = 1_656_938_975_000,
        disabledDate = null
    )

    @Test
    fun equalityIsStructural() {
        assertEquals(model(), model())
        assertNotEquals(illegal = model(), actual = model(id = "org-2"))
    }

    @Test
    fun identifierIsSeparateFromTheGeneratedId() {
        // `identifier` is the human-facing slug; `id` is Keycloak's group id.
        val organization = model()

        assertEquals(expected = "komune", actual = organization.identifier)
        assertEquals(expected = "org-1", actual = organization.id)
    }

    @Test
    fun attributesCarryArbitraryKeys() {
        assertEquals(expected = "12345678900011", actual = model().attributes["siret"])
    }

    @Test
    fun anOrganizationCanHaveNoDescriptionOrAddress() {
        val bare = model().copy(description = null, address = null)

        assertNull(bare.description)
        assertNull(bare.address)
    }

    @Test
    fun copyKeepsUntouchedFields() {
        val disabled = model().copy(enabled = false, disabledBy = "admin-1", disabledDate = 1)

        assertEquals(expected = false, actual = disabled.enabled)
        assertEquals(expected = "Komune", actual = disabled.displayName)
        assertEquals(expected = listOf("admin"), actual = disabled.roles)
    }
}
