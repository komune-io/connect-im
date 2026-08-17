package io.komune.im.script.gateway

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication

class ScriptApplicationTest {

    @Test
    fun `is a Spring Boot application`() {
        assertThat(ScriptApplication::class.java.getAnnotation(SpringBootApplication::class.java))
            .isNotNull()
    }

    @Test
    fun `the runner is a CommandLineRunner, which is what makes the script run at boot`() {
        assertThat(CommandLineRunner::class.java).isAssignableFrom(ScriptServiceRunner::class.java)
    }
}
