package city.smartb.im.apikey.api

import city.smartb.im.apikey.api.policies.ApiKeyPoliciesEnforcer
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddFunction
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyGetFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyPageFunction
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.lib.ApiKeyFeaturesImpl
import city.smartb.im.commons.auth.policies.enforce
import city.smartb.im.commons.auth.policies.verify
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @d2 service
 * @parent [city.smartb.im.apikey.domain.D2ApikeyPage]
 */
@RestController
@RequestMapping
@Service
class ApiKeyEndpoint(
    private val apikeyFeatures: ApiKeyFeaturesImpl<ApiKey>,
    private val apikeyPoliciesEnforcer: ApiKeyPoliciesEnforcer,
) {

    /**
     * Fetch an Apikey by its ID.
     */
    @Bean
    fun apiKeyGet(): ApiKeyGetFunction<ApiKey> = verify(apikeyFeatures.apiKeyGet()) { query ->
        apikeyPoliciesEnforcer.checkGet(query.id)
    }

    /**
     * Fetch a page of apikeys.
     */
    @Bean
    fun apiKeyPage(): ApiKeyPageFunction<ApiKey> = enforce(apikeyFeatures.apiKeyPage()) { query ->
        apikeyPoliciesEnforcer.enforcePage(query)
    }


    /**
     * Create an apikey.
     */
    @Bean
    fun apiKeyCreate(): ApiKeyOrganizationAddFunction = verify(apikeyFeatures.apiKeyCreate()) { command ->
        apikeyPoliciesEnforcer.checkCreate()
    }


    /**
     * Remove an API key from an apiKey.
     */
    @Bean
    fun apiKeyRemove(): ApikeyRemoveFunction = verify(apikeyFeatures.apiKeyRemove()) { command ->
        apikeyPoliciesEnforcer.apiKeyRemove(command.id)
    }



}
