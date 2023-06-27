package city.smartb.im.apikey.domain

import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddFunction
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveFunction

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
