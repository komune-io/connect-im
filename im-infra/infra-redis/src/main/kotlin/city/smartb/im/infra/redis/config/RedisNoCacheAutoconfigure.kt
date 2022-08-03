package city.smartb.im.infra.redis.config

import city.smartb.im.infra.redis.RedisCache
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair


@ConditionalOnMissingBean(RedisCache::class)
@Configuration(proxyBeanMethods=false)
class RedisNoCacheAutoconfigure {

    @Bean
    fun redisCache(
        objectMapper: ObjectMapper,
    ): RedisCache {
        return RedisCache(null, objectMapper)
    }
}
