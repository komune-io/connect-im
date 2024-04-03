package io.komune.im.commons.http

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson

object HttpClientBuilderJvm: ClientBuilder {
	override fun build(): HttpClient {
		return HttpClient(CIO) {
			install(ContentNegotiation) {
				jackson {
					configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
					propertyNamingStrategy = PropertyNamingStrategies.LOWER_CAMEL_CASE
				}
			}
		}
	}
}
