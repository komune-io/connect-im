package city.smartb.im.infra.redis.config

import city.smartb.im.infra.redis.RedisCache
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


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
