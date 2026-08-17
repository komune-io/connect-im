package io.komune.im.commons.utils

import f2.dsl.cqrs.page.OffsetPagination
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * In-memory paging for query endpoints. Out-of-range offsets must clamp rather than
 * throw, because they arrive straight from a client-supplied page number.
 */
class PageUtilsTest {

    private val items = (1..10).toList()

    @Test
    fun `no pagination returns everything with the real total`() {
        val page = items.page(null)

        assertThat(page.items).isEqualTo(items)
        assertThat(page.total).isEqualTo(10)
    }

    @Test
    fun `offset and limit take a window`() {
        val page = items.page(OffsetPagination(offset = 2, limit = 3))

        assertThat(page.items).containsExactly(3, 4, 5)
    }

    @Test
    fun `total is the unpaged size, not the window size`() {
        val page = items.page(OffsetPagination(offset = 0, limit = 2))

        assertThat(page.items).hasSize(2)
        assertThat(page.total).isEqualTo(10)
    }

    @Test
    fun `a limit past the end clamps instead of throwing`() {
        val page = items.page(offset = 8, limit = 100)

        assertThat(page.items).containsExactly(9, 10)
    }

    @Test
    fun `an offset past the end yields an empty page, not an exception`() {
        val page = items.page(offset = 50, limit = 10)

        assertThat(page.items).isEmpty()
        assertThat(page.total).isEqualTo(10)
    }

    @Test
    fun `a null limit means to the end`() {
        val page = items.page(offset = 7, limit = null)

        assertThat(page.items).containsExactly(8, 9, 10)
    }

    @Test
    fun `paging an empty list is empty rather than an error`() {
        val page = emptyList<Int>().page(offset = 0, limit = 10)

        assertThat(page.items).isEmpty()
        assertThat(page.total).isZero()
    }
}
