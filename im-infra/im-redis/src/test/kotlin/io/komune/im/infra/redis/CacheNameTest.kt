package io.komune.im.infra.redis

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Cache names become Redis key prefixes, so renaming one orphans every entry written
 * under the old name rather than failing loudly.
 */
class CacheNameTest {

    @Test
    fun `covers every cached aggregate`() {
        assertThat(CacheName.entries.map { it.name }).containsExactlyInAnyOrder(
            "Organization", "User", "Apikey", "Space", "Privilege", "Client"
        )
    }

    @Test
    fun `names are stable, since they are part of the redis key`() {
        assertThat(CacheName.Organization.name).isEqualTo("Organization")
        assertThat(CacheName.Apikey.name).isEqualTo("Apikey")
    }

    @Test
    fun `valueOf round-trips for every entry`() {
        CacheName.entries.forEach {
            assertThat(CacheName.valueOf(it.name)).isEqualTo(it)
        }
    }
}
