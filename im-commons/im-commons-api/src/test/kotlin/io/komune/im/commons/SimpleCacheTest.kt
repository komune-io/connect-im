package io.komune.im.commons

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SimpleCacheTest {

    @Test
    fun `fetches on the first get`() = runTest {
        val cache = SimpleCache<String, String> { "value-for-$it" }

        assertThat(cache.get("a")).isEqualTo("value-for-a")
    }

    @Test
    fun `fetches once per key, then serves from memory`() = runTest {
        var fetches = 0
        val cache = SimpleCache<String, String> { fetches++; "value" }

        repeat(3) { assertThat(cache.get("a")).isEqualTo("value") }

        assertThat(fetches).isEqualTo(1)
    }

    @Test
    fun `different keys fetch independently`() = runTest {
        var fetches = 0
        val cache = SimpleCache<String, String> { fetches++; it }

        assertThat(cache.get("a")).isEqualTo("a")
        assertThat(cache.get("b")).isEqualTo("b")

        assertThat(fetches).isEqualTo(2)
    }

    @Test
    fun `register pre-populates so no fetch happens`() = runTest {
        var fetches = 0
        val cache = SimpleCache<String, String> { fetches++; "fetched" }

        cache.register("a", "registered")

        assertThat(cache.get("a")).isEqualTo("registered")
        assertThat(fetches).isZero()
    }

    @Test
    fun `register overwrites an already cached value`() = runTest {
        val cache = SimpleCache<String, String> { "fetched" }

        assertThat(cache.get("a")).isEqualTo("fetched")
        cache.register("a", "replaced")

        assertThat(cache.get("a")).isEqualTo("replaced")
    }
}
