package io.komune.im.core.commons

import io.komune.im.infra.redis.CacheName
import io.komune.im.infra.redis.CachedService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Every core service extends this to inherit its cache. The cache name is what namespaces
 * the Redis keys, so it has to survive subclassing intact.
 */
class CoreServiceTest {

    private class TestCoreService : CoreService(CacheName.User)

    @Test
    fun `is a cached service, so subclasses get caching for free`() {
        assertThat(CachedService::class.java).isAssignableFrom(CoreService::class.java)
    }

    @Test
    fun `is open, since every core service subclasses it`() {
        assertThat(CoreService::class.java.modifiers and java.lang.reflect.Modifier.FINAL).isZero()
    }

    @Test
    fun `a subclass can be constructed with its own cache name`() {
        assertThat(TestCoreService()).isNotNull()
    }
}
