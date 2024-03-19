package io.komune.im.apikey.client

import io.komune.im.apikey.domain.ApiKeyCommandFeatures
import io.komune.im.apikey.domain.ApiKeyQueryFeatures
import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddFunction
import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddedEvent
import io.komune.im.apikey.domain.command.ApikeyRemoveEvent
import io.komune.im.apikey.domain.command.ApikeyRemoveFunction
import io.komune.im.apikey.domain.query.ApiKeyGetFunction
import io.komune.im.apikey.domain.query.ApiKeyGetResult
import io.komune.im.apikey.domain.query.ApiKeyPageFunction
import io.komune.im.apikey.domain.query.ApiKeyPageResult
import io.komune.im.commons.http.ClientBuilder
import io.komune.im.commons.http.ClientJvm
import io.komune.im.commons.http.HttpClientBuilderJvm
import f2.dsl.fnc.f2Function

class ApiKeyClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken), ApiKeyCommandFeatures, ApiKeyQueryFeatures {

    override fun apiKeyCreate(): ApiKeyOrganizationAddFunction = f2Function { cmd ->
        post<List<ApiKeyOrganizationAddedEvent>>("apiKeyCreate", cmd).first()
    }

    override fun apiKeyRemove(): ApikeyRemoveFunction = f2Function { cmd ->
        post<List<ApikeyRemoveEvent>>("apiKeyRemove", cmd).first()
    }

    override fun apiKeyGet(): ApiKeyGetFunction = f2Function { query ->
        post<List<ApiKeyGetResult>>("apiKeyGet", query).first()
    }

    override fun apiKeyPage(): ApiKeyPageFunction = f2Function { query ->
        post<List<ApiKeyPageResult>>("apiKeyPage", query).first()
    }
}
