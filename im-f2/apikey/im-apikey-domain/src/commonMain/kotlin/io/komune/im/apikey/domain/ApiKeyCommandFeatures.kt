package io.komune.im.apikey.domain

import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddFunction
import io.komune.im.apikey.domain.command.ApikeyRemoveFunction

interface ApiKeyCommandFeatures {
    /**
     * Create an api key.
     */
    fun apiKeyCreate(): ApiKeyOrganizationAddFunction

    /**
     * Create an api key.
     */
    fun apiKeyRemove(): ApikeyRemoveFunction
}
