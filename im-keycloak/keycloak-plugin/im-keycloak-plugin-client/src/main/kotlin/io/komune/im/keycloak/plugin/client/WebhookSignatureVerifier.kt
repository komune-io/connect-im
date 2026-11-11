package io.komune.im.keycloak.plugin.client

import java.util.Locale
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Utility for verifying HMAC-SHA256 signatures from Keycloak webhook events.
 *
 * Example usage:
 * ```kotlin
 * val verifier = WebhookSignatureVerifier("your-webhook-secret")
 *
 * // Verify a webhook request
 * val signatureHeader = request.headers["X-Webhook-Signature"] // e.g., "sha256=abc123..."
 * val payload = request.body // Raw JSON string
 *
 * if (!verifier.verify(payload, signatureHeader)) {
 *     throw UnauthorizedException("Invalid webhook signature")
 * }
 * ```
 */
class WebhookSignatureVerifier(
    private val secret: String
) {
    /**
     * Verifies the HMAC-SHA256 signature of a webhook payload.
     *
     * @param payload The raw JSON payload as a string
     * @param signatureHeader The value of the X-Webhook-Signature header (e.g., "sha256=abc123...")
     * @return true if the signature is valid, false otherwise
     */
    fun verify(payload: String, signatureHeader: String?): Boolean {
        if (signatureHeader.isNullOrBlank()) {
            return false
        }

        val expectedSignature = signatureHeader.removePrefix("sha256=")
        val actualSignature = generateSignature(payload)

        return constantTimeEquals(expected = expectedSignature, actual = actualSignature)
    }

    /**
     * Generates an HMAC-SHA256 signature for the given payload.
     *
     * @param payload The payload to sign
     * @return The hex-encoded signature
     */
    fun generateSignature(payload: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
        mac.init(secretKey)
        val hash = mac.doFinal(payload.toByteArray())
        return hash.joinToString("") { "%02x".format(Locale.ROOT, it) }
    }

    private fun constantTimeEquals(expected: String, actual: String): Boolean {
        if (expected.length != actual.length) {
            return false
        }

        var result = 0
        for (i in expected.indices) {
            result = result or (expected[i].code xor actual[i].code)
        }
        return result == 0
    }
}
