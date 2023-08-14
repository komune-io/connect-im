package city.smartb.im.commons.http

abstract class ClientJvm(
    baseUrl: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null },
): Client(
    baseUrl = baseUrl,
    generateBearerToken = generateBearerToken,
    httpClientBuilder = httpClientBuilder
)
