package io.komune.im.infra.redis

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Autowired

open class CachedService(
    protected val cacheName: CacheName
) {
    @Autowired
    protected lateinit var redisCache: RedisCache

    protected suspend inline fun <reified R> query(
        id: String,
        crossinline fetch: suspend CoroutineScope.() -> R
    ): R = redisCache.getFromCacheOr(cacheName, id) {
        coroutineScope {
            fetch()
        }
    }

    protected suspend inline fun <reified R> mutate(
        id: String,
        crossinline exec: suspend CoroutineScope.() -> R
    ): R = redisCache.evictIfPresent(cacheName, id) {
        coroutineScope {
            exec()
        }
    }
}
