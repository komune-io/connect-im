package city.smartb.im.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.SimpleKey

class RedisCache(
    val cacheManager: CacheManager?,
    val objectMapper: ObjectMapper,
) {

    companion object {
        val logger = LoggerFactory.getLogger(RedisCache::class.java)
    }

    inline fun <reified T> evictIfPresent(cacheName: CacheName, id: String, exec: () -> T): T {
        cacheManager?.getCache(cacheName.name.lowercase())?.evictIfPresent(id)
        return exec()
    }

    suspend inline fun <reified T> getFormCacheOr(cacheName: CacheName, vararg objectId: Any, fetch: () -> T): T {
        return cacheManager?.getCache(cacheName.name.lowercase())?.let { cache ->
            val key = SimpleKey(objectId)
            val value = cache.get(key)?.get() as String?
            if (value != null) {
                logger.debug("Cache[$cacheName] - id[$objectId] found in cache")
                objectMapper.readValue(value)
            } else {
                val newVal = fetch()
                logger.debug("Cache[$cacheName] - id[$objectId] not found in cache")
                val json = objectMapper.writeValueAsString(newVal)
                cache.put(key, json)
                newVal
            }
        } ?: fetch()
    }
}
