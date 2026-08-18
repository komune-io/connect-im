package io.komune.im.commons.utils

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KotlinUtilsTest {

    @Test
    fun `mapAsync preserves input order regardless of completion order`() = runTest {
        val result = listOf(1, 2, 3, 4).mapAsync { it * 2 }

        assertThat(result).containsExactly(2, 4, 6, 8)
    }

    @Test
    fun `mapAsync on an empty collection is empty`() = runTest {
        assertThat(emptyList<Int>().mapAsync { it }).isEmpty()
    }

    @Test
    fun `mapNotNullAsync drops nulls produced by the transform`() = runTest {
        val result = listOf(1, 2, 3, 4).mapNotNullAsync { if (it % 2 == 0) it else null }

        assertThat(result).containsExactly(2, 4)
    }

    @Test
    fun `mapNotNullAsync keeps order among the surviving elements`() = runTest {
        val result = (1..6).toList().mapNotNullAsync { if (it % 2 == 0) "v$it" else null }

        assertThat(result).containsExactly("v2", "v4", "v6")
    }

    @Test
    fun `mapAsyncDeferred hands back one deferred per element`() = runTest {
        val deferred = listOf("a", "b", "c").mapAsyncDeferred { it.uppercase() }

        assertThat(deferred).hasSize(3)
        assertThat(deferred.map { it.await() }).containsExactly("A", "B", "C")
    }

    @Test
    fun `a throwing transform propagates rather than being swallowed`() = runTest {
        val outcome = runCatching { listOf(1, 2).mapAsync { if (it == 2) error("boom") else it } }

        assertThat(outcome.isFailure).isTrue()
        assertThat(outcome.exceptionOrNull()).isInstanceOf(IllegalStateException::class.java)
    }
}
