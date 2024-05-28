package io.komune.im.commons.http

import f2.client.ktor.http.HttpClientBuilder
import f2.client.ktor.http.httpClientBuilder

abstract class ClientJvm(
    baseUrl: String,
    httpClientBuilder: HttpClientBuilder = httpClientBuilder(),
    generateBearerToken: suspend () -> String? = { null },
): Client(
    baseUrl = baseUrl,
    generateBearerToken = generateBearerToken,
    httpClientBuilder = httpClientBuilder
)
