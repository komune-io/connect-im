package city.smartb.im.commons.http

import city.smartb.im.commons.http.Client

abstract class ClientJvm(
    baseUrl: String,
    generateBearerToken: suspend () -> String? = { null },
): Client(
    baseUrl = baseUrl,
    httpClientBuilder = HttpClientBuilderJvm,
    generateBearerToken = generateBearerToken
)
