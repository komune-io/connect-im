package io.komune.im.commons.utils

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ParserUtilsTest {

    @Test
    fun `a missing configuration file is a configuration error`() {
        assertThatThrownBy { load("file:/nowhere/missing.json") }
            .isInstanceOf(ConfigurationException::class.java)
    }

    @Test
    fun `a missing classpath configuration is a configuration error`() {
        assertThatThrownBy { load("nowhere/missing.json") }
            .isInstanceOf(ConfigurationException::class.java)
    }

    private fun load(path: String) {
        val configuration = ParserUtils.getConfiguration(configPath = path, clazz = Map::class.java)
        error("loaded $configuration")
    }
}
