package city.smartb.im.commons.http

abstract class ClientJvm(
    baseUrl: String,
    generateBearerToken: suspend () -> String? = { null },
): Client(
    baseUrl = baseUrl,
    generateBearerToken = generateBearerToken,
    httpClientBuilder = HttpClientBuilderJvm
)
