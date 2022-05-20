package city.smartb.im.commons.http

import city.smartb.im.commons.http.ClientBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson

object HttpClientBuilderJvm: ClientBuilder {
	override fun build(): HttpClient {
		return HttpClient(CIO) {
			install(ContentNegotiation) {
				jackson()
			}
//			install(JsonFeature) {
//				serializer = JacksonSerializer {
//					this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//							.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//							.registerModule(KotlinModule())
//							.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
//				}
//			}
		}
	}
}
