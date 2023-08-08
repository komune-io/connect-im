package city.smartb.im.apikey.domain

import city.smartb.im.apikey.domain.features.query.ApiKeyGetFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyPageFunction
import city.smartb.im.apikey.domain.model.ApiKeyDTO

interface ApiKeyQueryFeatures<MODEL: ApiKeyDTO> {
    /**
     * Fetch an Apikey by its ID.
     */
    fun apiKeyGet(): ApiKeyGetFunction<MODEL>

    /**
     * Fetch a page of apikeys.
     */
    fun apiKeyPage(): ApiKeyPageFunction<MODEL>
}
