package io.komune.im.keycloak.plugin.rolemapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * This parses role/feature configuration out of Keycloak user attributes, which are
 * hand-edited, so the lenient reader settings are load-bearing.
 */
class JsonUtilsTest {

    data class Feature(val id: String = "", val enabled: Boolean = false)

    @Test
    fun `parses a json array to a list`() {
        val parsed: List<Feature> = """[{"id":"a","enabled":true},{"id":"b","enabled":false}]"""
            .parseJsonTo(Array<Feature>::class.java)

        assertThat(parsed).containsExactly(Feature("a", true), Feature("b", false))
    }

    @Test
    fun `an empty array parses to an empty list`() {
        val parsed: List<Feature> = "[]".parseJsonTo(Array<Feature>::class.java)

        assertThat(parsed).isEmpty()
    }

    @Test
    fun `unknown properties are ignored rather than failing the token mapper`() {
        val parsed: List<Feature> = """[{"id":"a","enabled":true,"removedField":1}]"""
            .parseJsonTo(Array<Feature>::class.java)

        assertThat(parsed).containsExactly(Feature("a", true))
    }

    @Test
    fun `unquoted property names are accepted`() {
        val parsed: List<Feature> = """[{id:"a",enabled:true}]"""
            .parseJsonTo(Array<Feature>::class.java)

        assertThat(parsed.single().id).isEqualTo("a")
    }
}
