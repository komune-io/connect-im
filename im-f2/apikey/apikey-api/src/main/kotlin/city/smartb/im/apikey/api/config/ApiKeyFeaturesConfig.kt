package city.smartb.im.apikey.api.config

import city.smartb.im.apikey.lib.ApiKeyFeaturesImpl
import city.smartb.im.apikey.lib.service.ApiKeyAggregateService
import city.smartb.im.apikey.lib.service.ApiKeyFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiKeyFeaturesConfig {

    @Bean
    fun apikeyFeatures(
        apikeyFinderService: ApiKeyFinderService,
        apikeyAggregateService: ApiKeyAggregateService,
    ) = ApiKeyFeaturesImpl(
        apikeyFinderService,
        apikeyAggregateService,
    )
}
