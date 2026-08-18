package io.komune.im.core.mfa.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Service

/**
 * Services here are wired by Spring component scanning and injected by type. Dropping the
 * stereotype, or making a class final so Spring cannot proxy it, fails at context startup
 * rather than at compile time — these catch it earlier.
 */
class SpaceOtpFlowServiceWiringTest {

    @Test
    fun `SpaceOtpFlowService is a Spring bean`() {
        assertThat(SpaceOtpFlowService::class.java.getAnnotation(Service::class.java)).isNotNull()
    }

}
