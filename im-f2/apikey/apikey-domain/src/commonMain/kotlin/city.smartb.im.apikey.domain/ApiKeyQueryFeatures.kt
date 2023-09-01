package city.smartb.im.apikey.domain

import city.smartb.im.apikey.domain.features.query.ApiKeyGetFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyPageFunction

interface ApiKeyQueryFeatures {
    /**
     * Fetch an Apikey by its ID.
     */
    fun apiKeyGet(): ApiKeyGetFunction

    /**
     * Fetch a page of apikeys.
     */
    fun apiKeyPage(): ApiKeyPageFunction
}
