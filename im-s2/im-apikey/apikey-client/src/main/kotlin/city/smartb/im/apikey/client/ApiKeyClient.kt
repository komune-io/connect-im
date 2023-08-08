package city.smartb.im.apikey.client

import city.smartb.im.apikey.domain.ApiKeyCommandFeatures
import city.smartb.im.apikey.domain.ApiKeyQueryFeatures
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddFunction
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyGetFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyPageFunction
import city.smartb.im.apikey.domain.model.ApiKeyDTO
import city.smartb.im.commons.http.ClientJvm
import f2.dsl.fnc.f2Function

class ApiKeyClient<MODEL: ApiKeyDTO>(
    url: String,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, generateBearerToken), ApiKeyCommandFeatures, ApiKeyQueryFeatures<MODEL> {

    override fun apiKeyCreate(): ApiKeyOrganizationAddFunction = f2Function { cmd ->
        post("apiKeyCreate", cmd)
    }

    override fun apiKeyRemove(): ApikeyRemoveFunction = f2Function { cmd ->
        post("apiKeyRemove", cmd)
    }

    override fun apiKeyGet(): ApiKeyGetFunction<MODEL> = f2Function { query ->
        post("apiKeyGet", query)
    }

    override fun apiKeyPage(): ApiKeyPageFunction<MODEL> = f2Function { query ->
        post("apiKeyPage", query)
    }
}
