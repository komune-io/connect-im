package io.komune.im.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.SimpleKey

class RedisCache(
    val cacheManager: CacheManager?,
    val objectMapper: ObjectMapper,
) {

    companion object {
        val logger = LoggerFactory.getLogger(RedisCache::class.java)
    }

    suspend fun <T> evictIfPresent(cacheName: CacheName, vararg id: String, exec: suspend () -> T): T {
        logger.debug("Cache[$cacheName] - evict[${id}]")
        val key = SimpleKey(id)
        return exec().also {
            getCache(cacheName)?.evictIfPresent(key)
        }
    }

    suspend inline fun <reified T> getFromCacheOr(
        cacheName: CacheName,
        vararg objectId: Any,
        crossinline fetch: suspend () -> T
    ): T {
        return getCache(cacheName)?.let { cache ->
            val key = SimpleKey(objectId)
            val value = cache.get(key)?.get() as String?
            if (value != null) {
                logger.debug("Cache[$cacheName] - id[${objectId.joinToString(", ")} - $key] found in cache")
                objectMapper.readValue(value)
            } else {
                logger.debug("Cache[$cacheName] - id[$key] not found in cache")
                fetch()?.also { newVal ->
                    putInCache<T>(newVal, cache, key)
                }

            }
        } ?: fetch()
    }

    suspend inline fun <reified T> putInCache(
        newVal: T & Any,
        cache: Cache,
        key: SimpleKey,
    ) {
        withContext(Dispatchers.IO) {
            val json = objectMapper.writeValueAsString(newVal)
            cache.put(key, json)
        }
    }

    fun getCache(cacheName: CacheName) = cacheManager?.getCache(cacheName.getName())

    private fun CacheName.getName() = name.lowercase()
}
