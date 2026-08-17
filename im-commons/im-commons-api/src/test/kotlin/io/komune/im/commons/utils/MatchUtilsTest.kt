package io.komune.im.commons.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * These drive query filtering: a null filter must mean "no constraint", never "match
 * nothing", or every unfiltered search would come back empty.
 */
class MatchUtilsTest {

    @Test
    fun `a null single-value filter matches anything`() {
        assertThat("anything".matches(null as String?)).isTrue()
    }

    @Test
    fun `a single-value filter matches only an equal value`() {
        assertThat("a".matches("a")).isTrue()
        assertThat("a".matches("b")).isFalse()
    }

    @Test
    fun `a null collection filter matches anything`() {
        assertThat("anything".matches(null as Collection<String>?)).isTrue()
    }

    @Test
    fun `a collection filter matches on membership`() {
        assertThat("a".matches(listOf("a", "b"))).isTrue()
        assertThat("c".matches(listOf("a", "b"))).isFalse()
    }

    @Test
    fun `an empty collection filter matches nothing`() {
        assertThat("a".matches(emptyList<String>())).isFalse()
    }

    @Test
    fun `a collection value matches when any element matches`() {
        assertThat(listOf("a", "z").matches(listOf("a", "b"))).isTrue()
        assertThat(listOf("y", "z").matches(listOf("a", "b"))).isFalse()
    }

    @Test
    fun `a collection value against a null filter matches`() {
        assertThat(listOf("a").matches(null)).isTrue()
    }

    @Test
    fun `an empty collection value never matches a non-null filter`() {
        assertThat(emptyList<String>().matches(listOf("a"))).isFalse()
    }
}
