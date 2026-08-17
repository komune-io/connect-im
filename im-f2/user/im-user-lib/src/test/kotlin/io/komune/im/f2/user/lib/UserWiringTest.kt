package io.komune.im.f2.user.lib

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Service

/**
 * Services here are wired by Spring component scanning and injected by type. Dropping the
 * stereotype, or making a class final so Spring cannot proxy it, fails at context startup
 * rather than at compile time — these catch it earlier.
 */
class UserWiringTest {

    @Test
    fun `UserAggregateService is a Spring bean`() {
        assertThat(UserAggregateService::class.java.getAnnotation(Service::class.java)).isNotNull()
    }

    @Test
    fun `UserFinderService is a Spring bean`() {
        assertThat(UserFinderService::class.java.getAnnotation(Service::class.java)).isNotNull()
    }

}
