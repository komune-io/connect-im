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

        cache.get("a")
        cache.get("a")
        cache.get("a")

        assertThat(fetches).isEqualTo(1)
    }

    @Test
    fun `different keys fetch independently`() = runTest {
        var fetches = 0
        val cache = SimpleCache<String, String> { fetches++; it }

        cache.get("a")
        cache.get("b")

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

        cache.get("a")
        cache.register("a", "replaced")

        assertThat(cache.get("a")).isEqualTo("replaced")
    }
}
