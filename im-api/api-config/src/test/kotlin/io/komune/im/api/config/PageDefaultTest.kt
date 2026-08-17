package io.komune.im.api.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * These back every unpaginated query, so they cap what an unbounded request returns.
 */
class PageDefaultTest {

    @Test
    fun `default page size stays bounded`() {
        assertThat(PageDefault.PAGE_SIZE).isEqualTo(10)
    }

    @Test
    fun `page numbering is one-based`() {
        assertThat(PageDefault.PAGE_NUMBER).isEqualTo(1)
    }

    @Test
    fun `the default page size is never unbounded`() {
        // A zero or negative default would turn every unpaginated query into a full scan.
        assertThat(PageDefault.PAGE_SIZE).isPositive()
    }
}
