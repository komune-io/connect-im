package io.komune.im.f2.space.client

import io.komune.im.commons.http.ClientBuilder
import io.komune.im.commons.http.ClientJvm
import io.komune.im.commons.http.HttpClientBuilderJvm

class SpaceClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken)
