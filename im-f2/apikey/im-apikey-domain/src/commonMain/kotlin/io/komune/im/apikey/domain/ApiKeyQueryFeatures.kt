package io.komune.im.apikey.domain

import io.komune.im.apikey.domain.query.ApiKeyGetFunction
import io.komune.im.apikey.domain.query.ApiKeyPageFunction

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
