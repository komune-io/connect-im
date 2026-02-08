package io.komune.im.infra.redis.config

import io.komune.im.infra.redis.RedisCache
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.ObjectMapper


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
