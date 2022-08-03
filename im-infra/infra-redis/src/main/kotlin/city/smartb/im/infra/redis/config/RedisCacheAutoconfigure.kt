package city.smartb.im.infra.redis.config

import city.smartb.im.infra.redis.RedisCache
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext


@ConditionalOnProperty(prefix = "spring.redis", name = ["password"])
@Configuration
class RedisCacheAutoconfigure {

    companion object {
        const val CACHE_TTL_MINUTE = 60L
    }

//    @Bean
//    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer {
//        return RedisCacheManagerBuilderCustomizer { builder: RedisCacheManager.RedisCacheManagerBuilder ->
//            builder
//                .withCacheConfiguration(
//                    "organization",
//                    RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofMinutes(10))
//                )
//
//        }
//    }

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
