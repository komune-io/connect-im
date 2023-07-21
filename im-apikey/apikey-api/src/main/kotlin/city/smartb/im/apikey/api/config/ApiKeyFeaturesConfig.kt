package city.smartb.im.apikey.api.config

import city.smartb.im.apikey.api.ApiKeyMapperImpl
import city.smartb.im.apikey.api.service.ApiKeyAggregateServiceDefault
import city.smartb.im.apikey.api.service.ApiKeyFinderServiceDefault
import city.smartb.im.apikey.lib.ApiKeyFeaturesImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiKeyFeaturesConfig {

    @Bean
    fun apikeyFeatures(
        apikeyFinderService: ApiKeyFinderServiceDefault,
        apikeyAggregateService: ApiKeyAggregateServiceDefault,
    ) = ApiKeyFeaturesImpl(
        apikeyFinderService,
        apikeyAggregateService,
        ApiKeyMapperImpl()
    )
}

