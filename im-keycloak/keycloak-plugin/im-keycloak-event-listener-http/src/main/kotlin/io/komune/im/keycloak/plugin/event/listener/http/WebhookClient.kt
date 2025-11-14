package io.komune.im.keycloak.plugin.event.listener.http

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.komune.im.keycloak.plugin.domain.model.KeycloakHttpEvent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.serialization.jackson.jackson
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object WebhookClient {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    private val objectMapper = jacksonObjectMapper()

    suspend fun send(url: String, event: KeycloakHttpEvent, secret: String? = null) {
        val payload = objectMapper.writeValueAsString(event)

        httpClient.post(url) {
            header("Content-Type", ContentType.Application.Json)

            // Add HMAC signature if secret is provided
            secret?.let {
                val signature = generateHmacSignature(payload, it)
                header("X-Webhook-Signature", "sha256=$signature")
            }

            // Use the same serialized payload for both signature and body
            setBody(payload)
        }
    }

    private fun generateHmacSignature(payload: String, secret: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
        mac.init(secretKey)
        val hash = mac.doFinal(payload.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}
