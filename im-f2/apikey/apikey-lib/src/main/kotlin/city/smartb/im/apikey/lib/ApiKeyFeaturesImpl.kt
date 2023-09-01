package city.smartb.im.apikey.lib

import city.smartb.im.apikey.domain.ApiKeyCommandFeatures
import city.smartb.im.apikey.domain.ApiKeyQueryFeatures
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddFunction
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyGetFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyPageFunction
import city.smartb.im.apikey.lib.service.ApiKeyAggregateService
import city.smartb.im.apikey.lib.service.ApiKeyFinderService
import f2.dsl.fnc.f2Function

class ApiKeyFeaturesImpl(
    private val apikeyFinderService: ApiKeyFinderService,
    private val apikeyAggregateService: ApiKeyAggregateService,
): ApiKeyQueryFeatures, ApiKeyCommandFeatures {
    private val logger by s2.spring.utils.logger.Logger()

    override fun apiKeyGet(): ApiKeyGetFunction = f2Function { query ->
        logger.debug("apikeyGet: $query")
        apikeyFinderService.apikeyGet(query)
    }

    override fun apiKeyPage(): ApiKeyPageFunction = f2Function { query ->
        logger.debug("apikeyPage: $query")
        apikeyFinderService.apikeyPage(query)
    }

    override fun apiKeyCreate(): ApiKeyOrganizationAddFunction = f2Function { cmd ->
        logger.debug("apikeyCreate: $cmd")
        apikeyAggregateService.addApiKey(cmd)
    }

    override fun apiKeyRemove(): ApikeyRemoveFunction = f2Function { cmd ->
        logger.debug("organizationDelete: $cmd")
        apikeyAggregateService.removeApiKey(cmd)
    }
}
