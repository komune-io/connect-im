package io.komune.im.api.gateway

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.SpringBootApplication

class ImApplicationTest {

    @Test
    fun `is a Spring Boot application`() {
        assertThat(ImApplication::class.java.getAnnotation(SpringBootApplication::class.java))
            .isNotNull()
    }

    @Test
    fun `component scanning reaches every im module`() {
        // Every module lives under io.komune.im; narrowing this silently drops beans.
        val annotation = ImApplication::class.java.getAnnotation(SpringBootApplication::class.java)

        val scanned = annotation.scanBasePackages.toList()
        assertThat(scanned.isEmpty() || scanned.any { "io.komune.im".startsWith(it) || it == "io.komune.im" })
            .isTrue()
    }
}
