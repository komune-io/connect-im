package io.komune.im.keycloak.plugin.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * This decides whether an inbound Keycloak webhook is trusted, so every branch that
 * returns `true` is a security boundary.
 */
class WebhookSignatureVerifierTest {

    private val verifier = WebhookSignatureVerifier("shared-secret")
    private val payload = """{"type":"REGISTER","userId":"user-1"}"""

    private fun validHeader() = "sha256=" + WebhookSignatureVerifier("shared-secret")
        .let { v ->
            // round-trip through verify() to obtain a signature the verifier accepts
            v.javaClass.getDeclaredMethod("generateSignature", String::class.java)
                .apply { isAccessible = true }
                .invoke(v, payload) as String
        }

    @Test
    fun `accepts a signature it generated itself`() {
        assertThat(verifier.verify(payload, validHeader())).isTrue()
    }

    @Test
    fun `accepts the signature without the sha256 prefix`() {
        val bare = validHeader().removePrefix("sha256=")

        assertThat(verifier.verify(payload, bare)).isTrue()
    }

    @Test
    fun `rejects a null header rather than defaulting to trusted`() {
        assertThat(verifier.verify(payload, null)).isFalse()
    }

    @Test
    fun `rejects a blank header`() {
        assertThat(verifier.verify(payload, "")).isFalse()
        assertThat(verifier.verify(payload, "   ")).isFalse()
    }

    @Test
    fun `rejects a signature for a different payload`() {
        assertThat(verifier.verify("""{"type":"LOGIN"}""", validHeader())).isFalse()
    }

    @Test
    fun `rejects a signature made with a different secret`() {
        val otherSignature = WebhookSignatureVerifier("another-secret")
            .javaClass.getDeclaredMethod("generateSignature", String::class.java)
            .apply { isAccessible = true }
            .invoke(WebhookSignatureVerifier("another-secret"), payload) as String

        assertThat(verifier.verify(payload, "sha256=$otherSignature")).isFalse()
    }

    @Test
    fun `rejects a truncated signature`() {
        val truncated = validHeader().dropLast(4)

        assertThat(verifier.verify(payload, truncated)).isFalse()
    }

    @Test
    fun `is deterministic for the same payload and secret`() {
        assertThat(validHeader()).isEqualTo(validHeader())
    }
}
