package io.komune.im.infra.redis.config

import io.komune.im.infra.redis.RedisCache
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration


@ConditionalOnProperty(prefix = "spring.redis", name = ["password"])
@Configuration
class RedisCacheAutoconfigure {

    companion object {
        const val CACHE_TTL_MINUTE = 60L
    }

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration? {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(CACHE_TTL_MINUTE))
            .disableCachingNullValues()
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                GenericJackson2JsonRedisSerializer()
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
