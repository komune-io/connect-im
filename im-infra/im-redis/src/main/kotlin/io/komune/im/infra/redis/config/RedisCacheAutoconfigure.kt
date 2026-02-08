package io.komune.im.infra.redis.config

import io.komune.im.infra.redis.RedisCache
import java.time.Duration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import tools.jackson.databind.ObjectMapper


@ConditionalOnProperty(prefix = "spring.redis", name = ["password"])
@Configuration
class RedisCacheAutoconfigure {

    companion object {
        const val CACHE_TTL_MINUTE = 60L
    }

    @Bean
    fun cacheConfiguration(objectMapper: ObjectMapper): RedisCacheConfiguration? {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(CACHE_TTL_MINUTE))
            .disableCachingNullValues()
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                GenericJacksonJsonRedisSerializer(objectMapper)
            ))
    }

    @Bean
    fun redisCache(
        cacheManager: CacheManager,
        objectMapper: ObjectMapper,
    ): RedisCache {
        return RedisCache(cacheManager, objectMapper)
    }

}
