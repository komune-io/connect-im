package city.smartb.im.apikey.lib

import city.smartb.im.apikey.domain.ApiKeyCommandFeatures
import city.smartb.im.apikey.domain.ApiKeyQueryFeatures
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddFunction
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyGetFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyPageFunction
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyDTO
import city.smartb.im.apikey.lib.service.ApiKeyAggregateService
import city.smartb.im.apikey.lib.service.ApiKeyFinderService
import city.smartb.im.apikey.lib.service.ApiKeyMapper
import f2.dsl.fnc.f2Function

class ApiKeyFeaturesImpl<MODEL: ApiKeyDTO>(
    private val apikeyFinderService: ApiKeyFinderService<MODEL>,
    private val apikeyAggregateService: ApiKeyAggregateService<MODEL>,
    private val apikeyMapper: ApiKeyMapper<ApiKey, MODEL>,
): ApiKeyQueryFeatures<MODEL>, ApiKeyCommandFeatures {
    private val logger by s2.spring.utils.logger.Logger()

    override fun apiKeyGet(): ApiKeyGetFunction<MODEL> = f2Function { query ->
        logger.debug("apikeyGet: $query")
        apikeyFinderService.apikeyGet(query, apikeyMapper)
    }

    override fun apiKeyPage(): ApiKeyPageFunction<MODEL> = f2Function { query ->
        logger.debug("apikeyPage: $query")
        apikeyFinderService.apikeyPage(query, apikeyMapper)
    }

    override fun apiKeyCreate(): ApiKeyOrganizationAddFunction = f2Function { cmd ->
        logger.debug("apikeyCreate: $cmd")
        apikeyAggregateService.addApiKey(cmd)
    }

    override fun apiKeyRemove(): ApikeyRemoveFunction = f2Function { cmd ->
        logger.debug("organizationDelete: $cmd")
        apikeyAggregateService.removeApiKey(cmd, apikeyMapper)
    }
}
